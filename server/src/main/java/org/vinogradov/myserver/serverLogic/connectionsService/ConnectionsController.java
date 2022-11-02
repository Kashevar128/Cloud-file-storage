package org.vinogradov.myserver.serverLogic.connectionsService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

public class ConnectionsController {

    UsersListChannels usersListChannels;
    ConnectionLimitRepository connectionLimitRepository;
    TemporaryDataBase temporaryDataBase;
    FileChannelRepository fileChannelRepository;

    public ConnectionsController() {
        this.usersListChannels = new UsersListChannels();
        this.connectionLimitRepository = new ConnectionLimitRepository();
        this.temporaryDataBase = new TemporaryDataBase();
        this.fileChannelRepository = new FileChannelRepository();
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
        String userName = usersListChannels.getUserNameByChannel(channel);
        usersListChannels.deleteUserChannelByChannel(channel);
        connectionLimitRepository.deleteConnectionLimit(channel);
        if (userName != null) {
            temporaryDataBase.deleteUserData(userName);
        }
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

    public void addUserInTemporaryDataBase(User user) {
        String name = user.getNameUser();
        String encryptedPassword =  DigestUtils.md5Hex(user.getPassword());
        temporaryDataBase.addUserData(name, encryptedPassword);
    }

    public boolean security(User user) {
        String name = user.getNameUser();
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword());
        String encryptedPasswordDataBase =temporaryDataBase.getUserPassword(name);
        if (encryptedPassword.equals(encryptedPasswordDataBase)) {
            return true;
        }
        return false;
    }

    public void addFileChannelUser(String dstPath) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(dstPath, true);
            fileChannelRepository.addFileChannel(dstPath, fileOutputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FileOutputStream getFileChannelUser(String dstPath) {
        return fileChannelRepository.getFileChannel(dstPath);
    }

    public void stopFileOutputStream(String dstPath) {
        FileOutputStream fileOutputStream = fileChannelRepository.getFileChannel(dstPath);
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        fileChannelRepository.deleteFileChannel(dstPath);
    }


}
