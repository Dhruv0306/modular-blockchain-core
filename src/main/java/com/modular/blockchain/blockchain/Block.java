package com.modular.blockchain.blockchain;

import com.modular.blockchain.transaction.Transaction;
import com.modular.blockchain.crypto.CryptoUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a block in the blockchain.
 * Each block contains multiple transactions and is linked to the previous block through its hash.
 */
public class Block {
    private final int index;
    private final long timestamp;
    private final List<Transaction> transactions;
    private final String previousHash;
    private String hash;
    private int nonce;
    private final String minerId;
    private final BlockHeader header;

    /**
     * Creates a new Block with the specified parameters.
     *
     * @param index The position of the block in the blockchain
     * @param timestamp The time when the block was created
     * @param transactions The list of transactions included in this block
     * @param previousHash The hash of the previous block in the chain
     * @param minerId The ID of the miner who created this block
     */
    public Block(int index, long timestamp, List<Transaction> transactions, String previousHash, String minerId) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.previousHash = previousHash;
        this.nonce = 0;
        this.minerId = minerId;
        this.header = new BlockHeader(index, timestamp, previousHash, BlockUtils.calculateMerkleRoot(transactions), nonce, minerId);
        this.hash = null;
    }

    /**
     * Mines the block by finding a hash that meets the difficulty requirement.
     * Increments the nonce until a hash with the required number of leading zeros is found.
     *
     * @param difficulty The number of leading zeros required in the hash
     */
    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            header.setNonce(nonce);
            hash = BlockUtils.calculateHash(getHeader(), getTransactions());
        }
    }

    /**
     * Converts the block to a JSON string representation.
     *
     * @return A JSON string containing all block data
     */
    public String toJson() {
        return BlockUtils.prettyPrint(this);
    }

    // Getters
    /**
     * @return The index of the block in the chain
     */
    public int getIndex() { return index; }

    /**
     * @return The timestamp when the block was created
     */
    public long getTimestamp() { return timestamp; }

    /**
     * @return The list of transactions in this block
     */
    public List<Transaction> getTransactions() { return transactions; }

    /**
     * @return The hash of the previous block
     */
    public String getPreviousHash() { return previousHash; }

    /**
     * @return The hash of this block
     */
    public String getHash() { return hash; }

    /**
     * @return The nonce used in mining this block
     */
    public int getNonce() { return nonce; }

    /**
     * @return The ID of the miner who created this block
     */
    public String getMinerId() { return minerId; }

    /**
     * @return The header of this block
     */
    public BlockHeader getHeader() { return header; }
}