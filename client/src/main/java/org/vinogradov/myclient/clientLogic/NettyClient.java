package org.vinogradov.myclient.clientLogic;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;
import org.vinogradov.mysupport.Constants;

public class NettyClient {

    private Channel channel = null;

    private User user;

    ClientHandlerLogicImpl clientHandlerLogicImpl;

    public NettyClient() throws InterruptedException {

        new Thread(() -> {
            EventLoopGroup eventLoopGroup = null;
            try {
                eventLoopGroup = new NioEventLoopGroup();
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(eventLoopGroup);
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.remoteAddress("localhost", 45001);
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(
                                new ObjectDecoder(Constants.MB_20, ClassResolvers.cacheDisabled(null)),
                                new ObjectEncoder(),
                                new ClientHandler(clientHandlerLogicImpl)
                        );
                    }
                });
                ChannelFuture channelFuture = bootstrap.connect().sync();
                channel = channelFuture.channel();
                channelFuture.channel().closeFuture().sync();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                eventLoopGroup.shutdownGracefully();
            }
        }).start();
    }

    public void sendMessage (BasicQuery basic) {
        channel.writeAndFlush(basic);
    }

    public void exitClient() {
        channel.closeFuture();
        channel.close();
    }

    public void setClientHandlerLogic(ClientHandlerLogicImpl clientHandlerLogicImpl) {
        this.clientHandlerLogicImpl = clientHandlerLogicImpl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

