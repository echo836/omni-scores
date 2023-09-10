package com.omni.score;

import score.Address;

import score.ByteArrayObjectWriter;
import score.Context;
import score.ObjectReader;
import score.ObjectWriter;

import score.DictDB;
import score.annotation.EventLog;
import score.annotation.External;
import score.annotation.Optional;
import score.annotation.Payable;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import com.omni.score.utils.IntSet;

import static com.omni.score.Vars.*;
import static com.omni.score.Constant.*;
import static com.omni.score.Message.*;
import static com.omni.score.xCalls.*;

public class IRC31 implements InterfaceIRC31 {

    public IRC31(String _name, String _symbol) {

        if (name.get() == null) {
            name.set(ensureNotEmpty(_name));
            symbol.set(ensureNotEmpty(_symbol));
            name.set(_name);
            symbol.set(_symbol);
            totalSupply.set(BigInteger.ZERO);

            for (BigInteger price : PRICES_DEFAULT) {
                prices.add(price);
            }

            for (String network : NETWORKS_DEFAULT) {
                supportedNetworks.add(network);
            }
        }
    }

    private String ensureNotEmpty(String str) {
        Context.require(str != null && !str.trim().isEmpty(), Message.empty("String"));
        assert str != null;
        return str.trim();
    }

    private String returnBtpAddress(String network, String address) {
        return "btp://" + network + "/" + address;
    }

    protected static Address extractAddressFromBtpAddress(String btpAddress) {
        int lastSlashIndex = btpAddress.lastIndexOf("/");

        if (lastSlashIndex != -1 && lastSlashIndex < btpAddress.length() - 1) {
            return Address.fromString(btpAddress.substring(lastSlashIndex + 1));
        } else {
            // Return an empty string or handle the case as needed
            return Address.fromString("hx0000000000000000000000000000000000000000");
        }
    }

    @External(readonly=true)
    public String name() {
        return name.get();
    }

    @External(readonly=true)
    public String symbol() {
        return symbol.get();
    }

    @External(readonly = true)
    public BigInteger getTotalSupply() {
        return totalSupply.get();
    }

    @External(readonly = true)
    public BigInteger getPrice(int index) {
        return prices.get(index);
    }

    @External(readonly = true)
    public Address getAdmin() {
        return admin.get();
    }

    @External(readonly = true)
    public BigInteger getNameCount() {
        return nameCount.getOrDefault(BigInteger.ZERO);
    }

    @External(readonly = true)
    public Address addressOf(String _name) {
        return nameMap.get(_name);
    }

    @External(readonly = true)
    public String nameOf(BigInteger _id) {
        return nameId.get(_id);
    }


    @External(readonly = true)
    public Address ownerOf(BigInteger _id) {
        return ownership.get(_id);
    }

    @External(readonly = true)
    public List allTokens(Address _address) {
        var tokens = userBalance.get(_address);
        BigInteger[] tokenList = new BigInteger[tokens.length()];
        for (int i = 0; i < tokens.length(); i++) {
            tokenList[i] = tokens.at(i);
        }
        return List.of(tokenList);
    }

    @External(readonly=true)
    public BigInteger tokenOfOwnerByIndex(Address _owner, int _index) {
        var tokens = userBalance.get(_owner);
        return (tokens != null) ? tokens.at(_index) : BigInteger.ZERO;
    }

    @External(readonly = true)
    public String getMainName(String _btpAddress) {
        return mainName.get(_btpAddress);
    }

    @External
    public void setAdmin(Address adminAddress) {
        this.ownerRequired();
        admin.set(adminAddress);
    }

    @External
    public void setPrice(BigInteger price, int index) {
        this.ownerRequired();
        prices.set(index,price);
    }

    @External(readonly=true)
    public BigInteger balanceOf(Address _owner, BigInteger _id) {
        return balances.at(_id).getOrDefault(_owner, BigInteger.ZERO);
    }

    @External(readonly=true)
    public BigInteger getExpiration(BigInteger _id) {
        return expirations.getOrDefault(_id, BigInteger.ZERO);
    }

    private void _addTokenTo(BigInteger tokenId, Address to) {
        var tokens = userBalance.get(to);
        if (tokens == null) {
            tokens = new IntSet(to.toString());
            userBalance.set(to, tokens);
        }
        tokens.add(tokenId);
        
    }

    private void _removeTokenFrom(BigInteger tokenId, Address from) {
        var tokens = userBalance.get(from);
        Context.require(tokens != null);
        tokens.remove(tokenId);
        if (tokens.length() == 0) {
            userBalance.set(from, null);
        }
    }

    @External(readonly=true)
    public BigInteger getPremium(BigInteger _id) {
        BigInteger expiration = getExpiration(_id);
        Long ts = Context.getBlockTimestamp();
        BigInteger now = BigInteger.valueOf(ts);
        BigInteger start = (expiration.add(GRACE));
        if (start.compareTo(now) >= 0) {
            return BigInteger.ZERO;
        } else {
            BigInteger end = start.add(PREMIUM_TIME);
            BigInteger timeRange = end.subtract(start);
            BigInteger currentTimePosition = now.subtract(start);

            BigInteger premiumRange = PREMIUM_MAX.subtract(PREMIUM_MIN);
            BigInteger premiumAtNow = PREMIUM_MIN.add(premiumRange.multiply(currentTimePosition).divide(timeRange));

            if (premiumAtNow.compareTo(PREMIUM_MIN) < 0) {
                premiumAtNow = PREMIUM_MIN;
            }
            return premiumAtNow;
        }
    }

    @External(readonly=true)
    public BigInteger[] balanceOfBatch(Address[] _owners, BigInteger[] _ids) {
        Context.require(_owners.length == _ids.length, Message.Not.sameSize("_owners","_ids"));

        BigInteger[] balances = new BigInteger[_owners.length];
        for (int i = 0; i < _owners.length; i++) {
            balances[i] = balanceOf(_owners[i], _ids[i]);
        }
        return balances;
    }

    public static String unescapeString(String input) {
        StringBuilder output = new StringBuilder();
        boolean escapeMode = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (escapeMode) {
                switch (c) {
                    case 'n':
                        output.append('\n');
                        break;
                    case 't':
                        output.append('\t');
                        break;
                    // Add more cases for other escape sequences as needed
                    default:
                        output.append(c);
                }
                escapeMode = false;
            } else {
                if (c == '\\') {
                    escapeMode = true;
                } else {
                    output.append(c);
                }
            }
        }

        return output.toString();
    }

    @External(readonly=true)
    public String tokenURI(BigInteger _tokenId) {
        return unescapeString(tokenURIs.get(_tokenId));
    }

    @External
    public void transferFrom(Address _from, Address _to, BigInteger _id, BigInteger _value, @Optional byte[] _data) {
        final Address caller = Context.getCaller();

        Context.require(!_to.equals(ZERO_ADDRESS), Message.Found.zeroAddr("_to"));
        Context.require(_from.equals(caller) || this.isApprovedForAll(_from, caller), Message.Not.operatorApproved());
        Context.require(BigInteger.ZERO.compareTo(_value) <= 0 && _value.compareTo(balanceOf(_from, _id)) <= 0,
                Message.Not.enoughBalance());

        // Transfer funds
        _addTokenTo(_id, _to);
        _removeTokenFrom(_id, _from);
        DictDB<Address, BigInteger> balance = balances.at(_id);
        balance.set(_from, balanceOf(_from, _id).subtract(BigInteger.ONE));
        balance.set(_to, balanceOf(_to, _id).add(BigInteger.ONE));
        ownership.set(_id, caller);

        String _name = nameId.get(_id);
        nameMap.set(_name, _to);
        // Emit event
        this.TransferSingle(caller, _from, _to, _id, _value);

        if (_to.isContract()) {
            // Call {@code onIRC31Received} if the recipient is a contract
            Context.call(_to, "onIRC31Received", caller, _from, _id, _value, _data == null ? new byte[]{} : _data);
        }
    }

    @External
    public void transferFromBatch(Address _from, Address _to, BigInteger[] _ids, BigInteger[] _values, @Optional byte[] _data) {
        final Address caller = Context.getCaller();

        Context.require(!_to.equals(ZERO_ADDRESS), Message.Found.zeroAddr("_to"));
        Context.require(_ids.length == _values.length, Message.Not.sameSize("_ids","_values"));
        Context.require(_from.equals(caller) || this.isApprovedForAll(_from, caller), Message.Not.operatorApproved());

        for (int i = 0; i < _ids.length; i++) {
            BigInteger _id = _ids[i];
            BigInteger _value = _values[i];

            Context.require(_value.compareTo(BigInteger.ZERO) > 0, Message.greaterThanZero("_value"));

            BigInteger balanceFrom = balanceOf(_from, _id);

            Context.require(_value.compareTo(balanceFrom) <= 0, Message.Not.enoughBalance());

            // Transfer funds
            BigInteger balanceTo = balanceOf(_to, _id);
            DictDB<Address, BigInteger> balance = balances.at(_id);
            _addTokenTo(_id, _to);
            _removeTokenFrom(_id, _from);
            balance.set(_from, balanceOf(_from, _id).subtract(BigInteger.ONE));
            balance.set(_to, balanceOf(_to, _id).add(BigInteger.ONE));
            ownership.set(_id, _to);
            String _name = nameId.get(_id);
            nameMap.set(_name, _to);
        }

        // Emit event
        this.TransferBatch(caller, _from, _to, rlpEncode(_ids), rlpEncode(_values));

        if (_to.isContract()) {
            // call {@code onIRC31BatchReceived} if the recipient is a contract
            Context.call(_to, "onIRC31BatchReceived",
                    caller, _from, _ids, _values, _data == null ? new byte[]{} : _data);
        }
    }

    @External
    public void setApprovalForAll(Address _operator, boolean _approved) {
        final Address caller = Context.getCaller();

        operatorApproval.at(caller).set(_operator, _approved);
        this.ApprovalForAll(caller, _operator, _approved);
    }

    @External(readonly=true)
    public boolean isApprovedForAll(Address _owner, Address _operator) {
        return operatorApproval.at(_owner).getOrDefault(_operator, false);
    }

    public static String replaceAllOccurrences(String input, String target, String replacement) {
        StringBuilder result = new StringBuilder(input);
        int targetLength = target.length();
        int currentIndex = input.lastIndexOf(target);

        while (currentIndex != -1) {
            result.replace(currentIndex, currentIndex + targetLength, replacement);
            currentIndex = input.lastIndexOf(target, currentIndex - 1);
        }

        return result.toString();
    }

    public static String generateSvg(String _name){
        
        Long randomFactor = Context.getBlockHeight();
        Long randomFactor2 = Context.getBlockTimestamp();

        int templateIndex = (int) (randomFactor % templates.size());
        int colorIndex = (int) (randomFactor2 % colors.size());

        String base = templates.get(templateIndex);
        String selectedColors = colors.get(colorIndex);
        
        base = replaceAllOccurrences(base, "{{color}}", selectedColors);

        String modifiedSvg = base.replace("{{replace}}", _name);

        return modifiedSvg;
    }

    @Payable
    @External
    public void registerName(String _name, BigInteger _years) {
        int nameLength = _name.length();
        Context.require(nameLength > 0, "Incorect name length.");
        Context.require(_years.signum() > 0 && _years.compareTo(BigInteger.valueOf(4)) <= 0 , "Incorect duration.");
        Context.require(nameMap.get(_name) == null, "Name taken");

        if (nameLength == 1){
            Context.require(Context.getValue().equals(prices.get(0).multiply(_years).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 2){
            Context.require(Context.getValue().equals(prices.get(1).multiply(_years).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 3){
            Context.require(Context.getValue().equals(prices.get(2).multiply(_years).multiply(POW18)), "Invalid payment");
        } else {
            Context.require(Context.getValue().equals(prices.get(3).multiply(_years).multiply(POW18)), "Invalid payment");
        }
        String uri = generateSvg(_name);
        _registerName(Context.getCaller(), uri, _name, _years);
    }

    private void _registerName(Address _owner, String _uri, String _name, BigInteger _years){
        BigInteger _id = this.getNameCount().add(BigInteger.ONE);
        _mintInternal(_owner, _id, BigInteger.ONE, _uri);
        Long ts = Context.getBlockTimestamp();
        BigInteger now = BigInteger.valueOf(ts);
        BigInteger exp = now.add(_years.multiply(ONE_YEAR));
        expirations.set(_id, exp);
        nameMap.set(_name, _owner);
        nameId.set(_id, _name);
        ownership.set(_id, _owner);
        var tokens = userBalance.get(_owner);
        if (tokens == null) {
            tokens = new IntSet(_owner.toString());
            userBalance.set(_owner, tokens);
        }
        tokens.add(_id);
        String iconId = supportedNetworks.get(0);
        crossChainBalance.set(_id, returnBtpAddress(iconId, _owner.toString()));
        nameCount.set(_id);
        TransferSingle(_owner, ZERO_ADDRESS, _owner, _id, BigInteger.ONE);
        ExpirationSet(_id, _name, exp);
    }

    @Payable
    @External
    public void renew(BigInteger _id, BigInteger _years) {
        Context.require(balanceOf(Context.getCaller(), _id).compareTo(BigInteger.ZERO) > 0, "You don't own this name");
        Context.require((expirations.get(_id).add(GRACE)).compareTo(BigInteger.valueOf(Context.getBlockHeight())) >= 0, "Grace period over");
        Context.require(_years.signum() > 0 && _years.compareTo(BigInteger.valueOf(4)) <= 0 , "Incorect duration.");
        String _name = this.nameOf(_id);
        int nameLength = _name.length();

        if (nameLength == 1){
            Context.require(Context.getValue().equals(prices.get(0).multiply(_years).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 2){
            Context.require(Context.getValue().equals(prices.get(1).multiply(_years).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 3){
            Context.require(Context.getValue().equals(prices.get(2).multiply(_years).multiply(POW18)), "Invalid payment");
        } else {
            Context.require(Context.getValue().equals(prices.get(3).multiply(_years).multiply(POW18)), "Invalid payment");
        }
        BigInteger oldExp = expirations.get(_id);
        BigInteger exp = oldExp.add(_years.multiply(ONE_YEAR));
        expirations.set(_id, exp);
        ExpirationSet(_id, _name, exp);
    }

    @Payable
    @External
    public void claim(BigInteger _id, BigInteger _years) {
        Context.require(getExpiration(_id).signum() > 0 , "Incorect id.");
        BigInteger premium = getPremium(_id);
        Context.require(premium.signum() > 0 , "Name not expiring.");

        String _name = this.nameOf(_id);
        int nameLength = _name.length(); 
        if (nameLength == 1){
            Context.require(Context.getValue().equals(prices.get(0).multiply(_years).add(premium).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 2){
            Context.require(Context.getValue().equals(prices.get(1).multiply(_years).add(premium).multiply(POW18)), "Invalid payment");
        } else if (nameLength == 3){
            Context.require(Context.getValue().equals(prices.get(2).multiply(_years).add(premium).multiply(POW18)), "Invalid payment");
        } else {
            Context.require(Context.getValue().equals(prices.get(3).multiply(_years).add(premium).multiply(POW18)), "Invalid payment");
        }
        Long ts = Context.getBlockTimestamp();
        BigInteger now = BigInteger.valueOf(ts);
        BigInteger exp = now.add(_years.multiply(ONE_YEAR));
        Address pastOwner = ownership.get(_id);
        Address caller = Context.getCaller();
        expirations.set(_id, exp);
        
        DictDB<Address, BigInteger> balance = balances.at(_id);
        // Transfer logic
        _addTokenTo(_id, caller);
        _removeTokenFrom(_id, pastOwner);
        balance.set(pastOwner, balanceOf(pastOwner, _id).subtract(BigInteger.ONE));
        balance.set(caller, balanceOf(caller, _id).add(BigInteger.ONE));
        ownership.set(_id, caller);
        nameMap.set(_name, caller);
        // Emit event
        this.TransferSingle(Context.getAddress(), pastOwner, caller, _id, BigInteger.ONE);

        if (caller.isContract()) {
            // Call {@code onIRC31Received} if the recipient is a contract
            Context.call(caller, "onIRC31Received", caller, pastOwner, _id, BigInteger.ONE, new byte[]{});
        }

        ExpirationSet(_id, _name, exp);
    }

    @External
    public void setMain(BigInteger _id) {
        Context.require(balanceOf(Context.getCaller(), _id).compareTo(BigInteger.ZERO) > 0, "You don't own this name");
        String name = nameOf(_id);
        String iconId = supportedNetworks.get(0);
        mainName.set(returnBtpAddress(iconId, Context.getCaller().toString()), name);
    }


    @External
    public void burn(Address _owner, BigInteger _id, BigInteger _amount) {
        this.preBurnConditions(_owner,_id,_amount);
        _burnInternal(_owner, _id, _amount);
        ownership.set(_id, ZERO_ADDRESS);
        TransferSingle(_owner, _owner, ZERO_ADDRESS, _id, _amount);
    }

    @External
    public void burnBatch(Address _owner, BigInteger[] _ids, BigInteger[] _amounts) {
        Context.require(_ids.length == _amounts.length, Message.Not.sameSize("_ids","_amounts"));

        for (int i = 0; i < _ids.length; i++) {
            BigInteger id = _ids[i];
            BigInteger amount = _amounts[i];
            this.preBurnConditions(_owner,id,amount);
            _burnInternal(_owner, id, amount);
        }

        TransferBatch(_owner, _owner, ZERO_ADDRESS, rlpEncode(_ids), rlpEncode(_amounts));
    }

    protected static byte[] rlpEncode(BigInteger[] ids) {
        Context.require(ids != null);

        ByteArrayObjectWriter writer = Context.newByteArrayObjectWriter("RLPn");

        writer.beginList(ids.length);
        for (BigInteger v : ids) {
            writer.write(v);
        }
        writer.end();

        return writer.toByteArray();
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

    private void _burnInternal(Address owner, BigInteger id, BigInteger amount) {
        BigInteger balance = balanceOf(owner, id);
        balances.at(id).set(owner, balance.subtract(amount));
        totalSupply.set(getTotalSupply().subtract(amount));
    }

    private void ownerRequired() {
        Context.require(Context.getCaller().equals(Context.getOwner()), Message.Not.owner());
    }

    private void adminRequired() {
        Context.require(Context.getCaller().equals(this.getAdmin()) || Context.getCaller().equals(Context.getOwner()),
                Message.Not.admin());
    }

    private void preBurnConditions(Address address, BigInteger id, BigInteger amount) {
        final Address caller = Context.getOrigin();
        Context.require(!address.equals(ZERO_ADDRESS), Message.Found.zeroAddr("_owner"));
        Context.require(amount.compareTo(BigInteger.ZERO) > 0, Message.greaterThanZero("_amount"));
        Context.require(address.equals(caller) || this.isApprovedForAll(address, caller),
                Message.Not.operatorApproved());
        Context.require(balanceOf(address,id).compareTo(amount)>=0,Message.Not.enoughBalance());
    }

    @EventLog(indexed=3)
    public void TransferSingle(Address _operator, Address _from, Address _to, BigInteger _id, BigInteger _value) {}

    @EventLog(indexed=3)
    public void TransferBatch(Address _operator, Address _from, Address _to, byte[] _ids, byte[] _values) {}

    @EventLog(indexed=2)
    public void ApprovalForAll(Address _owner, Address _operator, boolean _approved) {}

    @EventLog(indexed=1)
    public void URI(BigInteger _id, String _value) {}

    @EventLog(indexed=1)
    public void ExpirationSet(BigInteger _id, String _name, BigInteger _date ) {}

}
