package org.vinogradov.myserver.serverLogic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.OperationBan;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Map<Class<? extends BasicQuery>, BiConsumer<ChannelHandlerContext, BasicQuery>> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(RegClientRequest.class, ((channelHandlerContext, basicQuery) -> {
            channelHandlerContext.writeAndFlush(ServerHandlerLogic.getRegServerResponse((RegClientRequest) basicQuery, channelHandlerContext));
        }));

        REQUEST_HANDLERS.put(AuthClientRequest.class, ((channelHandlerContext, basicQuery) -> {
            channelHandlerContext.writeAndFlush(ServerHandlerLogic.getAuthServerResponse((AuthClientRequest) basicQuery, channelHandlerContext));
        }));

        REQUEST_HANDLERS.put(GetListRequest.class, ((channelHandlerContext, basicQuery) -> {
            channelHandlerContext.writeAndFlush(ServerHandlerLogic.getListResponse((GetListRequest) basicQuery));
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery request = (BasicQuery) msg;
        if (!ServerHandlerLogic.security(request)) {
            ctx.writeAndFlush(new OperationBan());
            return;
        }

        System.out.println(request.getType());
        BiConsumer<ChannelHandlerContext, BasicQuery> channelServerHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelServerHandlerContextConsumer.accept(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
