package com.modular.blockchain.consensus;

import com.modular.blockchain.blockchain.Block;

public class ConsensusResult {
    private final Block proposedBlock;
    private final boolean success;
    private final String message;

    public ConsensusResult(Block proposedBlock, boolean success, String message) {
        this.proposedBlock = proposedBlock;
        this.success = success;
        this.message = message;
    }

    public Block getProposedBlock() {
        return proposedBlock;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public static ConsensusResult ok(Block block) {
        return new ConsensusResult(block, true, "OK");
    }

    public static ConsensusResult fail(String message) {
        return new ConsensusResult(null, false, message);
    }
}
