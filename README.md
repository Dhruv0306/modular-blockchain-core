# modular-blockchain-core

A minimal, modular blockchain skeleton in Java — providing only core interfaces, abstractions, and extension points for transactions, consensus, wallets, and networking. No example implementations included.

---

## Features

- **Modular, extensible design**: All core blockchain infrastructure is provided, but consensus, wallet, and transaction logic are left abstract for user extension.
- **No example or placeholder implementations**: Only interfaces and abstract classes for extension points.
- **Ready-to-use infrastructure**: Includes block, blockchain, miner, crypto utilities, transaction pool, networking stubs, and a minimal REST API.
- **Lightweight logging**: All core modules use a simple Logger utility for INFO, ERROR, and DEBUG messages to aid debugging and traceability.

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
- `RestApiServer.java`: Minimal HTTP API exposing `/chain`, `/transaction`, and `/peers` endpoints for blockchain state, transaction submission, and peer listing. No longer manages miners or their mining process; miner management is now handled in the main application entry point. Transaction deserialization for `/transaction` is user-implemented.

### Application Entry Point
- `Main.java`: The main application entry point. Handles configuration, initializes the blockchain, transaction pool, consensus engine, wallet, miners, and starts the REST API server. Manages miner lifecycle and graceful shutdown.

---

## Logging

A lightweight Logger utility is included and integrated across all core modules. It provides timestamped INFO, ERROR, and DEBUG messages to the console for easier debugging and traceability.

**Logger usage example:**

```java
import com.modular.blockchain.util.Logger;

Logger.info("Blockchain started");
Logger.error("Failed to add block");
Logger.debug("Transaction pool size: " + pool.getBatch(10).size());
```

All major actions, errors, and important events in the blockchain, networking, transaction, and wallet modules are now logged using this utility.

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

Before running the application, you must provide your own implementations for the following:

- `Transaction` (extend the abstract interface for your transaction logic)
- `Wallet` (extend the abstract class for your wallet logic)
- `ConsensusEngine` (implement your consensus mechanism)

### 1. Plug in your Implementations

Open `src/main/java/com/modular/blockchain/Main.java` and update the following lines:

```java
// 1. ConsensusEngine implementation
ConsensusEngine consensusEngine = null; // TODO: Replace with actual implementation
// 2. Wallet implementation
Wallet wallet = null; // TODO: Replace with actual implementation
```

Replace the `null` assignments with your own classes, for example:

```java
ConsensusEngine consensusEngine = new MyConsensusEngine();
Wallet wallet = new MyWallet();
```

Also ensure your `Transaction` implementation is used wherever transactions are created or deserialized.

### 2. Build and Run

After updating `Main.java` with your implementations:

- Build the project using Maven:
  ```
  mvn compile
  ```
- Run the application:
  ```
  mvn exec:java -Dexec.mainClass="com.modular.blockchain.Main"
  ```

This will start the REST API server and miners as configured in `Main.java`.

---

## License

MIT License
