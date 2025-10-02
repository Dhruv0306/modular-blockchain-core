package com.modular.blockchain.transaction;

public interface Transaction {
    String getId();
    boolean isValid();
    String toJson();
}
