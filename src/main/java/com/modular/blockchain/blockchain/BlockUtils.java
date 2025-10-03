package com.modular.blockchain.blockchain;

import com.modular.blockchain.crypto.CryptoUtils;
import com.modular.blockchain.transaction.Transaction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class providing helper methods for blockchain block operations.
 */
public class BlockUtils {
    /**
     * Calculates the hash of a block based on its header and transactions.
     * @param header The block header containing metadata
     * @param transactions List of transactions in the block
     * @return SHA-256 hash of the block data
     */
    static String calculateHash(BlockHeader header, List<Transaction> transactions) {
        String dataToHash = header.toString() +
                transactions.stream()
                        .map(Transaction::toJson)
                        .collect(Collectors.joining());
        return CryptoUtils.sha256(dataToHash);
    }

    /**
     * Validates if a hash meets the required mining difficulty.
     * @param hash The hash to validate
     * @param difficulty The number of leading zeros required
     * @return true if hash meets difficulty requirement, false otherwise
     */
    static boolean isHashValid(String hash, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.substring(0, difficulty).equals(target);
    }

    /**
     * Creates a human-readable string representation of a block.
     * @param block The block to format
     * @return Formatted string containing block details
     */
    static String prettyPrint(Block block) {
        return "Block{" +
                "index=" + block.getIndex() +
                ", timestamp=" + block.getTimestamp() +
                ", transactions=" + block.getTransactions().stream()
                    .map(Transaction::toJson)
                    .collect(Collectors.joining(", ", "[", "]")) +
                ", previousHash='" + block.getPreviousHash() + '\'' +
                ", hash='" + block.getHash() + '\'' +
                ", nonce=" + block.getNonce() +
                '}';
    }

    /**
     * Calculates a simple Merkle root hash for a list of transactions.
     * @param txs List of transactions to hash
     * @return SHA-256 hash representing the Merkle root
     */
    static String calculateMerkleRoot(List<Transaction> txs) {
        // Simple concatenated hash for demo; replace with real Merkle root if needed
        String concat = txs.stream().map(Transaction::toJson).collect(Collectors.joining());
        return CryptoUtils.sha256(concat);
    }
}