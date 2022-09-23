package org.vinogradov.myserver.serverLogic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.requests.StartClientRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Map<Class<? extends BasicReqRes>, BiConsumer<ChannelHandlerContext, BasicReqRes>> REQUEST_HANDLERS = new HashMap<>();

    static {
        REQUEST_HANDLERS.put(StartClientRequest.class, ((channelHandlerContext, basicReqRes) -> {
            channelHandlerContext.writeAndFlush(ServerHandlerLogic.getStartServerResponse(basicReqRes));
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicReqRes request = (BasicReqRes) msg;
        System.out.println(request.getType());
        BiConsumer<ChannelHandlerContext, BasicReqRes> channelServerHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelServerHandlerContextConsumer.accept(ctx, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
