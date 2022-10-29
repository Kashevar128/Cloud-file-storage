package org.vinogradov.myserver.serverLogic.ConnectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;

public class ConnectionsController {

    UsersListChannels usersListChannels;

    public ConnectionsController() {
        this.usersListChannels = new UsersListChannels();
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
        usersListChannels.deleteUserChannelByChannel(context.channel());
    }
}
