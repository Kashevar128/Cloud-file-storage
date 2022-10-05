package org.vinogradov.myclient.clientLogic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

import java.util.HashMap;
import java.util.Map;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    NettyClient nettyClient;

    private static  final Map<Class<? extends BasicQuery>, MyTripleConsumer<ChannelHandlerContext, BasicQuery, NettyClient>> RESPONSE_HANDLERS = new HashMap<>();

    static {
        RESPONSE_HANDLERS.put(RegServerResponse.class, ((channelHandlerContext, basicQuery, nettyClient) -> {
            ClientHandlerLogic.getServerMessageReg((RegServerResponse) basicQuery);
        }));

        RESPONSE_HANDLERS.put(AuthServerResponse.class, ((channelHandlerContext, basicQuery, nettyClient) -> {
            ClientHandlerLogic.getServerMessageAuth((AuthServerResponse) basicQuery);
        }));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery response = (BasicQuery) msg;
        System.out.println(response.getType());
        MyTripleConsumer<ChannelHandlerContext, BasicQuery, NettyClient> channelClientHandlerContextConsumer = RESPONSE_HANDLERS.get(response.getClass());
        channelClientHandlerContextConsumer.accept(ctx, response, null);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
