package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;

public class ConnectionsController {

    UsersListChannels usersListChannels;
    ConnectionLimitRepository connectionLimitRepository;

    public ConnectionsController() {
        this.usersListChannels = new UsersListChannels();
        this.connectionLimitRepository = new ConnectionLimitRepository();
    }

    public Channel getUserChannel(String name) {
        return usersListChannels.getUserChannelByUserName(name);
    }

    public void putChannel(BasicQuery basicQuery, ChannelHandlerContext context) {
        User user = basicQuery.getUser();
        String name = user.getNameUser();
        usersListChannels.putUserChannel(name, context.channel());
    }

    public void unConnectUser(ChannelHandlerContext context) {
        Channel channel = context.channel();
        usersListChannels.deleteUserChannelByChannel(channel);
        connectionLimitRepository.deleteConnectionLimit(channel);
    }

    public void newConnectionLimit(ChannelHandlerContext context) {
            Channel channel = context.channel();
            connectionLimitRepository.addConnectionLimit(channel, new ConnectionLimit(channel));
    }

    public void stopTimerConnectionLimit(User user) {
        String name = user.getNameUser();
        Channel channel = usersListChannels.getUserChannelByUserName(name);
        ConnectionLimit connectionLimit = connectionLimitRepository.getConnectionLimitByChannel(channel);
        connectionLimit.stopTimer();
    }
}
