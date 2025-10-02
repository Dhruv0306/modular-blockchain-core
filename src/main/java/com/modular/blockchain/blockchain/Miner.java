package com.modular.blockchain.blockchain;

import com.modular.blockchain.consensus.ConsensusEngine;
import com.modular.blockchain.consensus.ConsensusResult;
import com.modular.blockchain.transaction.Transaction;
import com.modular.blockchain.transaction.TransactionPool;

import java.util.List;

public class Miner {
    private final TransactionPool pool;
    private final Blockchain blockchain;
    private final ConsensusEngine consensusEngine;
    private final String minerId;
    private final int miningThreshold;


    public Miner(String minerId, int miningThreshold, TransactionPool pool, Blockchain blockchain, ConsensusEngine consensusEngine) {
        this.minerId = minerId;
        this.miningThreshold = miningThreshold;
        this.pool = pool;
        this.blockchain = blockchain;
        this.consensusEngine = consensusEngine;
    }

    // Checks the pool and mines a block if enough transactions are present
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

    // Assembles a new block from a batch of transactions
    public Block assembleBlock(List<Transaction> batch) {
        Block latest = blockchain.getLatestBlock();
        int newIndex = latest.getIndex() + 1;
        long timestamp = System.currentTimeMillis();
        String previousHash = latest.getHash();
        return new Block(newIndex, timestamp, batch, previousHash, minerId);
    }
}
