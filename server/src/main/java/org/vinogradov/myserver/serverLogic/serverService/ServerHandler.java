package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.requests.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private final ServerLogic serverLogic;

    private static final Map<Class<? extends BasicQuery>, BiConsumer<ServerHandlerLogic, BasicQuery>> REQUEST_HANDLERS = new HashMap<>();

    public ServerHandler() {
        this.serverLogic = new ServerLogic();
    }

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

        REQUEST_HANDLERS.put(DelFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingDelFileRequest((DelFileRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(CreateNewFolderRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingCreateNewFolderRequest((CreateNewFolderRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(MetaDataFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingMetaDataFileRequest((MetaDataFileRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(SendPartFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingSendPartFileRequest((SendPartFileRequest) basicQuery);
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        serverLogic.setContext(ctx);
        serverLogic.addConnectionLimit(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery request = (BasicQuery) msg;

        if (!serverLogic.filterSecurity(request)) return;

        System.out.println(request.getType());
        BiConsumer<ServerHandlerLogic, BasicQuery> channelServerHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelServerHandlerContextConsumer.accept(serverLogic, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
