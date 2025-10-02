package com.modular.blockchain.blockchain;

import com.modular.blockchain.transaction.Transaction;
import com.modular.blockchain.crypto.CryptoUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Block {
    private final int index;
    private final long timestamp;
    private final List<Transaction> transactions;
    private final String previousHash;
    private String hash;
    private int nonce;
    private final String minerId;
    private final BlockHeader header;

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

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            header.setNonce(nonce);
            hash = BlockUtils.calculateHash(getHeader(), getTransactions());
        }
    }

    public String toJson() {
        return BlockUtils.prettyPrint(this);
    }

    // Getters
    public int getIndex() { return index; }
    public long getTimestamp() { return timestamp; }
    public List<Transaction> getTransactions() { return transactions; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
    public int getNonce() { return nonce; }
    public String getMinerId() { return minerId; }
    public BlockHeader getHeader() { return header; }
}
