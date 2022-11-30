package org.vinogradov.myclient.clientService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.responses.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    private final ClientLogic clientLogic;

    private static  final Map<Class<? extends BasicQuery>, BiConsumer<BasicQuery, ClientHandlerLogic>> RESPONSE_HANDLERS = new HashMap<>();

    public ClientHandler(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }

    static {

        RESPONSE_HANDLERS.put(RegOrAuthServerResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingRegOrAuthResponse((RegOrAuthServerResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(GetListResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingGetListResponse((GetListResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(ConnectionLimitResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingConnectionLimit((ConnectionLimitResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(PermissionToTransferResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingPermissionToTransferResponse((PermissionToTransferResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(MetaDataResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingMetaDataResponse((MetaDataResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(SendPartFileResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingSendPartFileResponse((SendPartFileResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(PatternMatchingResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingPatternMatchingResponse((PatternMatchingResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(ClearClientMapResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingClearClientMapResponse((ClearClientMapResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(NotCreateNewPathResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingNotCreateNewPathResponse((NotCreateNewPathResponse) basicQuery);
        });

        RESPONSE_HANDLERS.put(TheUserIsAlreadyLoggedIn.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingTheUserIsAlreadyLoggedIn((TheUserIsAlreadyLoggedIn) basicQuery);
        });

        RESPONSE_HANDLERS.put(OverwriteFileResponse.class, (basicQuery, clientHandlerLogic) -> {
            clientHandlerLogic.getHandingOverwriteFileResponse((OverwriteFileResponse) basicQuery);
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
        System.out.println(response.getClassName());
        BiConsumer<BasicQuery, ClientHandlerLogic> channelClientHandlerContextConsumer = RESPONSE_HANDLERS.get(response.getClass());
        channelClientHandlerContextConsumer.accept(response, clientLogic);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
