package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserContextRepository {

    private final ConcurrentMap<String, ChannelHandlerContext> userChannelHandlerContextMap;

    private final ConcurrentMap<ChannelHandlerContext, String> channelHandlerContextUserMap;

    public UserContextRepository() {
        userChannelHandlerContextMap = new ConcurrentHashMap<>();
        channelHandlerContextUserMap = new ConcurrentHashMap<>();
    }

    public boolean addUserChannelHandlerContextMap(String user, ChannelHandlerContext context) {
         if(userChannelHandlerContextMap.containsKey(user)) return false;
        userChannelHandlerContextMap.put(user, context);
        channelHandlerContextUserMap.put(context, user);
        return true;
    }

    public void deleteUserContext(String nameUser) {
        ChannelHandlerContext channelHandlerContext = userChannelHandlerContextMap.get(nameUser);
        userChannelHandlerContextMap.remove(nameUser);
        channelHandlerContextUserMap.remove(channelHandlerContext);
    }

    public void deleteUserContext(ChannelHandlerContext context) {
        String user = channelHandlerContextUserMap.get(context);
        channelHandlerContextUserMap.remove(context);
        userChannelHandlerContextMap.remove(user);
    }

    public ChannelHandlerContext getContext(String user) {
        return userChannelHandlerContextMap.get(user);
    }

    public String getUser(ChannelHandlerContext context) {
        return channelHandlerContextUserMap.get(context);
    }

}
