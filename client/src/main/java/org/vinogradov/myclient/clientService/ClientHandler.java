package org.vinogradov.myclient.clientService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.responses.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private ClientLogic clientLogic;

    private static  final Map<Class<? extends BasicQuery>, BiConsumer<BasicQuery, ClientHandlerLogic>> RESPONSE_HANDLERS = new HashMap<>();

    public ClientHandler(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }

    static {
        RESPONSE_HANDLERS.put(RegServerResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingMessageReg((RegServerResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(AuthServerResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingMessageAuth((AuthServerResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(GetListResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingMessageList((GetListResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(ConnectionLimitResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingConnectionLimit();
        });

        RESPONSE_HANDLERS.put(MetaDataFileResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingMetaDataResponse((MetaDataFileResponse) basicQuery);
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress());
        clientLogic.setContext(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery response = (BasicQuery) msg;
        System.out.println(response.getType());
        BiConsumer<BasicQuery, ClientHandlerLogic> channelClientHandlerContextConsumer = RESPONSE_HANDLERS.get(response.getClass());
        channelClientHandlerContextConsumer.accept(response, clientLogic);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
