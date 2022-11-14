package org.vinogradov.myserver.serverLogic.connectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.common.commonClasses.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionsController {

    private Channel userChannel;
    private ConnectionLimit connectionLimit;
    private User user;
    private final ConcurrentMap<String, FileOutputStream> channelOutPutStreamMap = new ConcurrentHashMap<>();

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

    public void addFileChannelUser(String dstPath) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(dstPath, true);
            channelOutPutStreamMap.put(dstPath, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FileOutputStream getFileChannelUser(String dstPath) {
        return channelOutPutStreamMap.get(dstPath);
    }

    public void stopFileOutputStream(String dstPath) {
        FileOutputStream fileOutputStream = channelOutPutStreamMap.get(dstPath);
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        channelOutPutStreamMap.remove(dstPath);
    }


}
