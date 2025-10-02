package com.modular.blockchain.blockchain;

public class BlockHeader {
    private final int index;
    private final long timestamp;
    private final String previousHash;
    private final String merkleRoot;
    private int nonce;
    private final String minerId;

    public BlockHeader(int index, long timestamp, String previousHash, String merkleRoot, int nonce, String minerId) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.merkleRoot = merkleRoot;
        this.nonce = nonce;
        this.minerId = minerId;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return index + timestamp + previousHash + merkleRoot + nonce + minerId;
    }
}
