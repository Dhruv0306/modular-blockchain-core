package com.modular.blockchain.consensus;

import com.modular.blockchain.blockchain.Block;
import com.modular.blockchain.blockchain.Blockchain;
import com.modular.blockchain.transaction.Transaction;
import java.util.List;

public interface ConsensusEngine {
    Block mineBlock(List<Transaction> txs, Blockchain blockchain, String minerId);
    ConsensusResult validateBlock(Block block, Blockchain blockchain);
}
