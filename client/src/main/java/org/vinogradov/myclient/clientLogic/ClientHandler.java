package org.vinogradov.myclient.clientLogic;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.responses.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private ClientHandlerLogic handlerLogic;

    private static  final Map<Class<? extends BasicQuery>, BiConsumer<BasicQuery, ClientHandlerLogic>> RESPONSE_HANDLERS = new HashMap<>();

    static {
        RESPONSE_HANDLERS.put(RegServerResponse.class, (basicQuery, handlerLogic) -> {
            handlerLogic.getHandingMessageReg((RegServerResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(AuthServerResponse.class, (basicQuery, handlerLogic) -> {
            handlerLogic.getHandingMessageAuth((AuthServerResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(GetListResponse.class, (basicQuery, handlerLogic) -> {
            handlerLogic.getHandingMessageList((GetListResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(OperationBanResponse.class, (basicQuery, handlerLogic) -> {
            handlerLogic.getHandingOperationBan();
        });

        RESPONSE_HANDLERS.put(ConnectionLimitResponse.class, (basicQuery, handlerLogic) -> {
            handlerLogic.getHandingConnectionLimit();
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery response = (BasicQuery) msg;
        System.out.println(response.getType());
        BiConsumer<BasicQuery, ClientHandlerLogic> channelClientHandlerContextConsumer = RESPONSE_HANDLERS.get(response.getClass());
        channelClientHandlerContextConsumer.accept(response, handlerLogic);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public ClientHandler(ClientHandlerLogicImpl handlerLogic) {
        this.handlerLogic = handlerLogic;
    }
}
