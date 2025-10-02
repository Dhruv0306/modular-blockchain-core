package com.modular.blockchain.blockchain;

import com.modular.blockchain.crypto.CryptoUtils;
import com.modular.blockchain.transaction.Transaction;

import java.util.List;
import java.util.stream.Collectors;

public class BlockUtils {
    static String calculateHash(BlockHeader header, List<Transaction> transactions) {
        String dataToHash = header.toString() +
                transactions.stream()
                        .map(Transaction::toJson)
                        .collect(Collectors.joining());
        return CryptoUtils.sha256(dataToHash);
    }
    static boolean isHashValid(String hash, int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.substring(0, difficulty).equals(target);
    }

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

    static String calculateMerkleRoot(List<Transaction> txs) {
        // Simple concatenated hash for demo; replace with real Merkle root if needed
        String concat = txs.stream().map(Transaction::toJson).collect(Collectors.joining());
        return CryptoUtils.sha256(concat);
    }
}
