package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UsersListChannels {

    private final ConcurrentMap<String, Channel> usersChannels = new ConcurrentHashMap<>();

    private final ConcurrentMap<Channel, String> usersChannelsReverse = new ConcurrentHashMap<>();


    public void putUserChannel(String name, Channel channelUser) {
        if (!usersChannels.containsKey(name)) {
            usersChannels.put(name, channelUser);
            usersChannelsReverse.put(channelUser, name);
        }
    }

    public void deleteUserChannelByUserName(String nameUser) {
        if (usersChannels.containsKey(nameUser)) {
            Channel channel = usersChannels.get(nameUser);
            usersChannels.remove(nameUser);
            usersChannelsReverse.remove(channel);
        }
    }

    public void deleteUserChannelByChannel(Channel channel) {
        if (usersChannelsReverse.containsKey(channel)) {
            String nameUser = usersChannelsReverse.get(channel);
            usersChannelsReverse.remove(channel);
            usersChannels.remove(nameUser);
        }

    }

    public Channel getUserChannelByUserName(String nameUser) {
        return usersChannels.get(nameUser);
    }

    public String getUserNameByChannel(Channel userChannel) {
        return usersChannelsReverse.get(userChannel);
    }
}
