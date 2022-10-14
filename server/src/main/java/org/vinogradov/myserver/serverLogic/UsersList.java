package org.vinogradov.myserver.serverLogic;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UsersList {

    private final ConcurrentMap<String, Channel> usersChannels = new ConcurrentHashMap<>();

    private final ConcurrentMap<Channel, String> usersChannelsReverse = new ConcurrentHashMap<>();


    public void putUserChannel(String name, Channel channelUser) {
        usersChannels.put(name, channelUser);
        usersChannelsReverse.put(channelUser, name);
    }

    public void deleteUserChannel(String nameUser) {
        Channel channel = usersChannels.get(nameUser);
        usersChannels.remove(nameUser);
        usersChannelsReverse.remove(channel);
    }

    public void deleteUserChannel(Channel channel) {
        String nameUser = usersChannelsReverse.get(channel);
        usersChannelsReverse.remove(channel);
        usersChannels.remove(nameUser);
    }

    public Channel getUserChannel(String nameUser) {
        return usersChannels.get(nameUser);
    }

    public String getUserChannel(Channel userChannel) {
        return usersChannelsReverse.get(userChannel);
    }
}
