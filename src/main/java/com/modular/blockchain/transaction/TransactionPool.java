package com.modular.blockchain.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionPool {
    private final List<Transaction> pendingTransactions = new ArrayList<>();

    public synchronized void addTransaction(Transaction tx) {
        if (tx.isValid()) pendingTransactions.add(tx);
    }

    public synchronized List<Transaction> getBatch(int size) {
        if (pendingTransactions.isEmpty()) return Collections.emptyList();
        int batchSize = Math.min(size, pendingTransactions.size());
        return new ArrayList<>(pendingTransactions.subList(0, batchSize));
    }

    public synchronized void removeTransactions(List<Transaction> txs) {
        pendingTransactions.removeAll(txs);
    }
}
