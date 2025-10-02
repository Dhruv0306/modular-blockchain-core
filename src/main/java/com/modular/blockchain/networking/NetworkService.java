package com.modular.blockchain.networking;

import com.modular.blockchain.blockchain.Block;
import com.modular.blockchain.transaction.Transaction;
import java.util.ArrayList;
import java.util.List;

public class NetworkService {
    private final List<Peer> peers = new ArrayList<>();

    public void broadcastBlock(Block block) {
        System.out.println("[NetworkService] Broadcasting block: " + block.getHash());
    }

    public void broadcastTransaction(Transaction tx) {
        System.out.println("[NetworkService] Broadcasting transaction: " + tx.getId());
    }

    public void addPeer(Peer peer) {
        peers.add(peer);
        System.out.println("[NetworkService] Added peer: " + peer.getId());
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public void removePeer(Peer peer) {
        peers.remove(peer);
        System.out.println("[NetworkService] Removed peer: " + peer.getId());
    }
}
