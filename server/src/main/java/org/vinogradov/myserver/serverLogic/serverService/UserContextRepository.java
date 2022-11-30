package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class UserContextRepository {

    private final ConcurrentMap<User, ChannelHandlerContext> userChannelHandlerContextMap;

    private final ConcurrentMap<ChannelHandlerContext, User> channelHandlerContextUserMap;

    public UserContextRepository() {
        userChannelHandlerContextMap = new ConcurrentHashMap<>();
        channelHandlerContextUserMap = new ConcurrentHashMap<>();
    }

    public void addUserChannelHandlerContextMap(User user, ChannelHandlerContext context) {
        for (User us : userChannelHandlerContextMap.keySet()) {
            if(us.equals(user)) return;
        }
        userChannelHandlerContextMap.put(user, context);
        channelHandlerContextUserMap.put(context, user);
    }

    public void deleteUserContext(User user) {
        ChannelHandlerContext channelHandlerContext = userChannelHandlerContextMap.get(user);
        userChannelHandlerContextMap.remove(user);
        channelHandlerContextUserMap.remove(channelHandlerContext);
    }

    public void deleteUserContext(ChannelHandlerContext context) {
        User user = channelHandlerContextUserMap.get(context);
        channelHandlerContextUserMap.remove(context);
        userChannelHandlerContextMap.remove(user);
    }

    public ChannelHandlerContext getContext(User user) {
        return userChannelHandlerContextMap.get(user);
    }

    public User getUser(ChannelHandlerContext context) {
        return channelHandlerContextUserMap.get(context);
    }

}
