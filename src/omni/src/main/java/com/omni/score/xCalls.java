package com.omni.score;

import score.*;

import java.math.BigInteger;

import static com.omni.score.Constant.*;
import static com.omni.score.Vars.*;

import static com.omni.score.IRC31.*;
import score.ByteArrayObjectWriter;
import score.annotation.EventLog;
import score.annotation.External;
import score.annotation.Optional;
import score.annotation.Payable;


public class xCalls {

    @External(readonly=true)
    public String btpAddress() {
        return btpAddress.get();
    }

    @External(readonly=true)
    public String getServiceContractAt(String _chain) {
        return servicesContracts.get(_chain);
    }

    @External(readonly=true)
    public BigInteger balanceOf(Address _owner, BigInteger _id) {
        return balances.at(_id).getOrDefault(_owner, BigInteger.ZERO);
    }

    @External(readonly = true)
    public BigInteger getTotalSupply() {
        return totalSupply.get();
    }

    private void ownerRequired() {
        Context.require(Context.getCaller().equals(Context.getOwner()), Message.Not.owner());
    }

    private void onlyCallService() {
        Context.require(Context.getCaller().equals(extractAddressFromBtpAddress(btpAddress())), "onlyBtpAddress");
    }

    @External
    public void setBtpAddress(String _address) {
        ownerRequired();
        btpAddress.set(_address);
    }

    private String isolateChain(String _chain) {
        int startIndex = _chain.indexOf("//") + 2; // Adding 2 to exclude "//"
        int endIndex = _chain.indexOf(".", startIndex);
        
        if (startIndex >= 0 && endIndex > startIndex) {
            return _chain.substring(startIndex, endIndex);
        } else {
            return null; // Return null if no valid substring found
        }
    }


    private BigInteger _sendCallMessage(BigInteger value, String to, byte[] data, byte[] rollback) {
        try {
            Context.call(value, extractAddressFromBtpAddress(btpAddress()), "sendCallMessage", to, data, rollback);
            return BigInteger.ONE;
        } catch (UserRevertedException e) {
            // propagate the error code to the caller
            Context.revert(e.getCode(), "UserReverted");
            return BigInteger.ZERO; // call flow does not reach here, but make compiler happy
        }
    }

    private void _setTokenURI(BigInteger _id, String _uri) {
        Context.require(_uri.length() > 0, Message.empty("URI"));
        tokenURIs.set(_id, _uri);
        this.URI(_id, _uri);
    }

    protected void _mintInternal(Address owner, BigInteger id, BigInteger amount,String uri) {
        BigInteger balance = balanceOf(owner, id);
        balances.at(id).set(owner, balance.add(amount));
        totalSupply.set(getTotalSupply().add(amount));
        _setTokenURI(id,uri);
    }

    private BigInteger _mintAndLockNft(BigInteger _years, String _name, String _from, String _uri) {
        int nameLength = _name.length();
        Context.require(nameLength > 0, "Incorect name length.");
        Context.require(_years.signum() > 0 && _years.compareTo(BigInteger.valueOf(4)) <= 0 , "Incorect duration.");
        Context.require(nameMap.get(_name) == null, "Name taken");
        BigInteger _id = nameCount.getOrDefault(BigInteger.ZERO).add(BigInteger.ONE);

        _registerNameAndLock(_id, _from, _uri, _name, _years);
        return _id;
    }

    private String sanitizeName(String _name) {
        // Throw error if name contains space
        if (_name.contains(" ")) {
            Context.revert(10, "Space in name"); 
        }
        if (_name.length() > 18) {
            Context.revert(11, "Name is too long"); 
        }
        // Convert name to lowercase
        return _name.toLowerCase();
    }

    private void _registerNameAndLock(BigInteger _id, String _owner, String _uri, String _name, BigInteger _years){
        _mintInternal(Context.getAddress(), _id, BigInteger.ONE, _uri);
        Long ts = Context.getBlockTimestamp();
        BigInteger now = BigInteger.valueOf(ts);
        BigInteger exp = now.add(_years.multiply(ONE_YEAR));
        String sanitizedName = sanitizeName(_name);
        expirations.set(_id, exp);
        nameMap.set(sanitizedName, Context.getAddress());
        nameId.set(_id, sanitizedName);
        
        ownership.set(_id, Context.getAddress());
        crossChainBalance.set(_id, _owner);
        nameCount.set(_id);
        TransferSingle(Context.getAddress(), ZERO_ADDRESS, Context.getAddress(), _id, BigInteger.ONE);
        ExpirationSet(_id, sanitizedName, exp);
    }

    @External
    public void handleCallMessage(String _from, byte[] _data){
        onlyCallService();
        MessageReceived(_from, _data);
        // Get the destination chain
        String _chain = isolateChain(_from);
        // Get the expected caller
        String expectedCaller = getServiceContractAt(_chain); 
        if (btpAddress().equals(_from)) { // rollback
            String message = new String(_data);
            String[] parts = message.split(",");
            if (parts.length == 3) {
                String method = parts[0];
                if (method == "mint"){
                    // cancel mint if rollbacked
                }
            } else {
                Context.revert(0, "Invalid rollback");
            }
        } else if (expectedCaller.equals(_from)) {
            String message = new String(_data);
            String[] parts = message.split(",");
            String method = parts[0];
            if (method == "mint"){
                String name = parts[1];
                String owner = parts[2];
                BigInteger year = new BigInteger(parts[3]);
                // mint logic
                String _uri = generateSvg(name);
                BigInteger _id = _mintAndLockNft(year, name, owner, _uri);
                byte[] _mintMessage = ("mint,"+_id.toString()+','+owner+','+_uri).getBytes();
                byte[] _rollbackMessage = ("mint,"+_id.toString()).getBytes();
                _sendCallMessage(BigInteger.ZERO, expectedCaller, _mintMessage, _rollbackMessage);
            }
            // TODO: Handle renew, claim
        }
        

    }


    @EventLog(indexed=2)
    public void MessageReceived(String _from, byte[] _data ) {}

    @EventLog(indexed=3)
    public void TransferSingle(Address _operator, Address _from, Address _to, BigInteger _id, BigInteger _value) {}

    @EventLog(indexed=1)
    public void ExpirationSet(BigInteger _id, String _name, BigInteger _date ) {}

    @EventLog(indexed=1)
    public void URI(BigInteger _id, String _value) {}
}
