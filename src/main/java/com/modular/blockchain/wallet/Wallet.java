package com.modular.blockchain.wallet;

import com.modular.blockchain.crypto.KeyPairInfo;
import com.modular.blockchain.transaction.Transaction;

public abstract class Wallet {
    protected final String userId;
    protected final KeyPairInfo keys;

    public Wallet(String userId, KeyPairInfo keys) {
        this.userId = userId;
        this.keys = keys;
    }

    public String getUserId() {
        return userId;
    }

    public KeyPairInfo getKeys() {
        return keys;
    }

    public abstract byte[] sign(Transaction tx);

    public abstract String getAddress();

    public abstract Transaction signTransaction(Transaction tx);
}
