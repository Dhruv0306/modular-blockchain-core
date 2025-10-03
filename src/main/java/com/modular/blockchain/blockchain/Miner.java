package com.modular.blockchain.blockchain;

import com.modular.blockchain.consensus.ConsensusEngine;
import com.modular.blockchain.consensus.ConsensusResult;
import com.modular.blockchain.transaction.Transaction;
import com.modular.blockchain.transaction.TransactionPool;

import java.util.List;

/**
 * Represents a miner in the blockchain network that is responsible for creating new blocks
 * by validating and combining transactions from the transaction pool.
 */
public class Miner {
    private final TransactionPool pool;
    private final Blockchain blockchain;
    private final ConsensusEngine consensusEngine;
    private final String minerId;
    private final int miningThreshold;

    /**
     * Constructs a new Miner instance.
     *
     * @param minerId Unique identifier for this miner
     * @param miningThreshold Minimum number of transactions required to create a block
     * @param pool Transaction pool to get pending transactions from
     * @param blockchain Reference to the blockchain
     * @param consensusEngine Engine used to validate blocks and achieve consensus
     */
    public Miner(String minerId, int miningThreshold, TransactionPool pool, Blockchain blockchain, ConsensusEngine consensusEngine) {
        this.minerId = minerId;
        this.miningThreshold = miningThreshold;
        this.pool = pool;
        this.blockchain = blockchain;
        this.consensusEngine = consensusEngine;
    }

    /**
     * Checks the transaction pool and attempts to mine a new block if enough transactions are present.
     * The block is only added to the blockchain if it passes consensus validation.
     */
    public void checkAndMine() {
        List<Transaction> batch = pool.getBatch(miningThreshold);
        if (batch == null || batch.isEmpty()) {
            return; // Nothing to mine
        }
        Block newBlock = assembleBlock(batch);
        ConsensusResult result = consensusEngine.validateBlock(consensusEngine.mineBlock(batch, blockchain,minerId), blockchain);
        if (result != null && result.isSuccess()) {
            blockchain.addBlock(newBlock);
            pool.removeTransactions(batch);
            // Optionally: broadcast the new block here
        }
        // else: consensus failed, do not add block
    }

    /**
     * Assembles a new block from a batch of transactions.
     *
     * @param batch List of transactions to include in the block
     * @return A new Block instance containing the provided transactions
     */
    public Block assembleBlock(List<Transaction> batch) {
        Block latest = blockchain.getLatestBlock();
        int newIndex = latest.getIndex() + 1;
        long timestamp = System.currentTimeMillis();
        String previousHash = latest.getHash();
        return new Block(newIndex, timestamp, batch, previousHash, minerId);
    }
}