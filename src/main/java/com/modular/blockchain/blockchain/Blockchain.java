package com.modular.blockchain.blockchain;

import com.modular.blockchain.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private final List<Block> blocks;
    private final int difficulty;

    public Blockchain(int difficulty){
        this.blocks = new ArrayList<Block>();
        this.difficulty = difficulty;
        // Create genesis block
        Block genesisBlock = genesisBlock();
        genesisBlock.mineBlock(difficulty);
        blocks.add(genesisBlock);
    }

    Block getLatestBlock() {
        return blocks.getLast();
    }

    void addBlock(Block block) {
        block.mineBlock(difficulty);

        if (BlockUtils.isHashValid(block.getHash(), difficulty)) {
            blocks.add(block);
        }

    }

    private boolean isChainValid() {
        for (int i = 1; i < blocks.size(); i++) {
            Block currentBlock = blocks.get(i);
            Block previousBlock = blocks.get(i - 1);

            if (!currentBlock.getHash().equals(BlockUtils.calculateHash(currentBlock.getHeader(), currentBlock.getTransactions()))) {
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                return false;
            }
        }
        return true;
    }

    public Block genesisBlock() {
        // Example values, adjust as needed for your Block constructor
        String genesisPreviousHash = "0";
        String genesisData = "Genesis Block";
        long timestamp = System.currentTimeMillis();
        int nonce = 0;
        List<Transaction> emptyTransactions = List.of();

        BlockHeader header = new BlockHeader(0, timestamp,genesisPreviousHash, "", nonce, "genesis");
        return new Block(0, timestamp, emptyTransactions, genesisPreviousHash, "genesis");
    }

    public List<Block> getChain() {
        return new ArrayList<>(blocks);
    }
}
