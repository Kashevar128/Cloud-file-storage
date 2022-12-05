package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.vinogradov.common.commonClasses.Constants;
import org.vinogradov.myserver.serverLogic.consoleService.ServerConsole;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

public class NettyServer {
    private final ChannelFuture channelFuture;

    private final UserContextRepository userContextRepository;
    private final Storage storage;
    private final DataBase dataBase;
    private final ServerConsole serverConsole;

    private final  EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public NettyServer() throws InterruptedException {
        try {
            this.userContextRepository = new UserContextRepository();
            this.storage = new Storage();
            this.dataBase = new DataBaseImpl();
            this.serverConsole = new ServerConsole(dataBase, storage, this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline inbound = socketChannel.pipeline();
                            inbound.addLast(
                                    new ObjectDecoder(Constants.MB_10, ClassResolvers.cacheDisabled(null)),
                                    new ObjectEncoder(),
                                    new ServerHandler(dataBase, storage, NettyServer.this)
                            );
                        }
                    });
            this.channelFuture = serverBootstrap.bind(45001).sync();
            System.out.println("Start server");
            channelFuture.channel().closeFuture().sync();
        } finally {
            closeServer();
        }
    }

    public void closeServer() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        dataBase.closeDataBase();
    }

    public UserContextRepository getUserContextRepository() {
        return userContextRepository;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Storage getStorage() {
        return storage;
    }
}
