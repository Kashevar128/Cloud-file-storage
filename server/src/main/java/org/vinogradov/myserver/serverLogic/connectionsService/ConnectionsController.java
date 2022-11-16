package org.vinogradov.myserver.serverLogic.connectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.common.commonClasses.User;

public class ConnectionsController {

    private Channel userChannel;
    private ConnectionLimit connectionLimit;
    private User user;

    public Channel getUserChannel() {
        return userChannel;
    }

    public void putChannel(ChannelHandlerContext context) {
        this.userChannel = context.channel();
    }

    public void newConnectionLimit(ChannelHandlerContext context) {
            Channel channel = context.channel();
            connectionLimit = new ConnectionLimit(channel);
    }

    public void stopTimerConnectionLimit() {
        connectionLimit.stopTimer();
    }

    public void addDataUser(User user) {
        String name = user.getNameUser();
        String encryptedPassword =  DigestUtils.md5Hex(user.getPassword());
        this.user = new User(name, encryptedPassword);

    }

    public boolean security(User user) {
        String name = user.getNameUser();
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword());
        return  (name.equals(this.user.getNameUser()) && encryptedPassword.equals(this.user.getPassword()));
    }


}
