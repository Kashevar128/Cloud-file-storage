package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionLimitRepository {

    private final ConcurrentMap<Channel, ConnectionLimit> limitTimeConnections;

    public ConnectionLimitRepository() {
        this.limitTimeConnections = new ConcurrentHashMap();
    }

    public void addConnectionLimit(Channel channel, ConnectionLimit connectionLimit) {
        if (!limitTimeConnections.containsKey(channel)) {
            limitTimeConnections.put(channel, connectionLimit);
        }
    }

    public void deleteConnectionLimit(Channel channel) {
        if (limitTimeConnections.containsKey(channel)) {
            limitTimeConnections.remove(channel);
        }
    }

    public ConnectionLimit getConnectionLimitByChannel(Channel channel) {
        return limitTimeConnections.get(channel);
    }
}
