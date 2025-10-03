package com.modular.blockchain.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages a pool of pending transactions that have not yet been added to the blockchain.
 * Provides thread-safe methods to add, retrieve and remove transactions from the pool.
 */
public class TransactionPool {
    private final List<Transaction> pendingTransactions = new ArrayList<>();

    /**
     * Adds a new transaction to the pending pool if it is valid.
     * This method is synchronized to ensure thread-safety.
     *
     * @param tx The transaction to add to the pool
     */
    public synchronized void addTransaction(Transaction tx) {
        if (tx.isValid()) pendingTransactions.add(tx);
    }

    /**
     * Retrieves a batch of pending transactions up to the specified size.
     * This method is synchronized to ensure thread-safety.
     *
     * @param size The maximum number of transactions to retrieve
     * @return A list containing up to 'size' transactions, or an empty list if no transactions are pending
     */
    public synchronized List<Transaction> getBatch(int size) {
        if (pendingTransactions.isEmpty()) return Collections.emptyList();
        int batchSize = Math.min(size, pendingTransactions.size());
        return new ArrayList<>(pendingTransactions.subList(0, batchSize));
    }

    /**
     * Removes the specified transactions from the pending pool.
     * This method is synchronized to ensure thread-safety.
     *
     * @param txs The list of transactions to remove
     */
    public synchronized void removeTransactions(List<Transaction> txs) {
        pendingTransactions.removeAll(txs);
    }
}