package org.vinogradov.myclient.clientLogic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.responses.StartServerResponse;

import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    NettyClient nettyClient;

    private static  final Map<Class<? extends BasicReqRes>, MyTripleConsumer<ChannelHandlerContext, BasicReqRes, NettyClient>> RESPONSE_HANDLERS = new HashMap<>();

    static {
        RESPONSE_HANDLERS.put(StartServerResponse.class, ((channelHandlerContext, basicReqRes, nettyClient) -> {
            System.out.println("Обновили список");
        }));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicReqRes response = (BasicReqRes) msg;
        System.out.println(response.getType());
        MyTripleConsumer<ChannelHandlerContext, BasicReqRes, NettyClient> channelClientHandlerContextConsumer = RESPONSE_HANDLERS.get(response.getClass());
        channelClientHandlerContextConsumer.accept(ctx, response, null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
