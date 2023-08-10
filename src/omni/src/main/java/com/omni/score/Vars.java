package com.omni.score;

import score.*;

import java.math.BigInteger;

import static com.omni.score.Constant.*;
import static score.Context.newVarDB;
import static score.Context.newArrayDB;

public class Vars {

    // NFT standard
    static final Address ZERO_ADDRESS = new Address(new byte[Address.LENGTH]);
    static final VarDB<String> name = Context.newVarDB(NAME, String.class);
    static final VarDB<String> symbol = Context.newVarDB(SYMBOL, String.class);
    static final VarDB<BigInteger> totalSupply = Context.newVarDB(TOTAL_SUPPLY, BigInteger.class);
    static final BranchDB<BigInteger, DictDB<Address, BigInteger>> balances = Context.newBranchDB(BALANCES, BigInteger.class);
    static final BranchDB<Address, DictDB<Address, Boolean>> operatorApproval = Context.newBranchDB(APPROVAL, Boolean.class);
    static final DictDB<BigInteger, String> tokenURIs = Context.newDictDB(TOKEN_URI, String.class);
    static final VarDB<Address> admin = newVarDB(ADMIN, Address.class);
    
    // ICON ONS logic
    static final VarDB<BigInteger> nameCount = newVarDB(COUNT, BigInteger.class);
    static final DictDB<String, Address> nameMap = Context.newDictDB(NAME_MAP, Address.class);
    static final DictDB<BigInteger, String> nameId = Context.newDictDB(NAME_ID, String.class);
    static final DictDB<BigInteger, Address> ownership = Context.newDictDB(OWNERSHIP, Address.class);
    static final DictDB<BigInteger, BigInteger> expirations = Context.newDictDB(EXPIRATIONS, BigInteger.class);
    static final ArrayDB<BigInteger> prices = newArrayDB(PRICES, BigInteger.class);

    // Cross-chain logic
    static final ArrayDB<String> supportedNetworks = Context.newArrayDB(SUPPORTED_NETWORKS, String.class);
    static final DictDB<String, String> mainName = Context.newDictDB(MAIN, String.class);
    static final DictDB<BigInteger, String> crossChainBalance = Context.newDictDB(CROSS_CHAIN_BALANCE, String.class);
    static final VarDB<String> btpAddress = newVarDB(BTP, String.class);
    static final DictDB<String, String> servicesContracts = Context.newDictDB(SERVICE_CONTRACTS, String.class);

}
