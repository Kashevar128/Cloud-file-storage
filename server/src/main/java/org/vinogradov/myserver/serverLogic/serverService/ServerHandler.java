package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.requests.SendFileRequest;
import org.vinogradov.myserver.serverLogic.ConnectionsService.ConnectionLimit;
import org.vinogradov.myserver.serverLogic.ConnectionsService.ConnectionsController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerLogic serverLogic;

    private static final Map<Class<? extends BasicQuery>, BiConsumer<ServerHandlerLogic, BasicQuery>> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(RegClientRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.sendRegServerResponse((RegClientRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(AuthClientRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.sendAuthServerResponse((AuthClientRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(GetListRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.sendListResponse((GetListRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(SendFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingSendFileRequest((SendFileRequest) basicQuery);
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery request = (BasicQuery) msg;

        serverLogic.getConnectionsController().putChannel(request, ctx);

        System.out.println(request.getType());
        BiConsumer<ServerHandlerLogic, BasicQuery> channelServerHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelServerHandlerContextConsumer.accept(serverLogic, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        serverLogic.getConnectionsController().newConnectionLimit(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        serverLogic.getConnectionsController().unConnectUser(ctx);
    }

    public ServerHandler(ServerLogic serverLogic) {
        this.serverLogic = serverLogic;
    }
}
