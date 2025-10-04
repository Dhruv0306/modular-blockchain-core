# modular-blockchain-core

A minimal, modular blockchain skeleton in Java — providing only core interfaces, abstractions, and extension points for transactions, consensus, wallets, and networking. No example implementations included.

---

## Features

- **Modular, extensible design**: All core blockchain infrastructure is provided, but consensus, wallet, and transaction logic are left abstract for user extension.
- **No example or placeholder implementations**: Only interfaces and abstract classes for extension points.
- **Ready-to-use infrastructure**: Includes block, blockchain, miner, crypto utilities, transaction pool, networking stubs, and a minimal REST API.

---

## Core Components

### Blockchain
- `Block.java`: Immutable block structure with index, timestamp, transactions, previousHash, hash, nonce, minerId, and BlockHeader. Hashing and mining logic use BlockHeader and include minerId/nonce for uniqueness.
- `Blockchain.java`: Manages a list of blocks and chain difficulty. Handles genesis block creation, block addition, and chain validation. No transaction pool logic present.
- `BlockHeader.java`: Contains block metadata (index, timestamp, previousHash, merkleRoot, nonce, minerId) for use in block hashing.
- `BlockUtils.java`: Static helpers for block hash calculation, hash validation, pretty printing, and Merkle root computation (simple concatenated hash).
- `Miner.java`: Monitors the transaction pool and periodically attempts to mine new blocks using a scheduled executor. Mining is started/stopped with `startMining(int minutes)` and `stopMining()`. Assembles blocks, delegates mining/validation to the consensus engine, and only adds blocks if consensus is successful. Removes included transactions from the pool after successful mining.

### Consensus
- `ConsensusEngine.java`: Abstract interface for consensus logic, with methods for block validation and mining (to be implemented by users).
- `ConsensusResult.java`: DTO for consensus outcomes, including proposed block, success flag, and message, with static factory methods for clean API usage.

### Crypto
- `CryptoUtils.java`: Implements SHA-256 hashing, RSA keypair generation, and digital signature (sign/verify) utilities.
- `KeyPairInfo.java`: Wrapper for public/private key pairs, with safe Base64 encoding methods.

### Networking
- `NetworkService.java`: Stub for peer-to-peer networking, with methods to broadcast blocks/transactions, add/remove peers, and list peers.
- `Peer.java`: Represents a network peer with id, address, and port. Implements equals/hashCode for collection use.

### Transactions
- `Transaction.java`: Abstract interface for transactions, with required methods for id, validation, and JSON serialization.
- `TransactionPool.java`: Thread-safe pool for pending transactions, with batch retrieval and removal.

### Wallet
- `Wallet.java`: Abstract class for user wallets, with required methods for signing, address retrieval, and transaction signing.
- `WalletStore.java`: Manages wallet references only; no example wallets included.

### API
- `RestApiServer.java`: Minimal HTTP API exposing `/chain`, `/transaction`, and `/peers` endpoints for blockchain state, transaction submission, and peer listing. Manages a list of miners and starts/stops their mining process when the server starts/stops. Transaction deserialization for `/transaction` is user-implemented.

---

## Extension Points

- **Consensus**: Implement your own consensus logic by extending `ConsensusEngine`.
- **Wallet**: Implement your own wallet logic by extending `Wallet`.
- **Transaction**: Implement your own transaction types by extending `Transaction`.

---

## Design Principles

- **No business logic in core**: All business logic (consensus, wallet, transaction) is left to the user.
- **No example or placeholder implementations**: Only clean, minimal interfaces and abstract classes.
- **Ready for extension**: All core infrastructure is provided and ready for user-defined logic.

---

## Getting Started

1. Implement your own `Transaction`, `Wallet`, and `ConsensusEngine` classes.
2. Wire them into the provided infrastructure (blockchain, miner, networking, API).
3. Extend or replace stubs as needed for your use case.

---

## License

MIT License
