package com.modular.blockchain.wallet;

import java.util.HashMap;
import java.util.Map;

public class WalletStore {
    private final Map<String, Wallet> store = new HashMap<>();

    public void addWallet(Wallet w) {
        store.put(w.getUserId(), w);
    }

    public Wallet getWallet(String userId) {
        return store.get(userId);
    }

    public void removeWallet(String userId) {
        store.remove(userId);
    }
}
