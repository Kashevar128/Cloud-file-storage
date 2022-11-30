package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.requests.*;
import org.vinogradov.myserver.serverLogic.connectionService.ConnectionsController;

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

        REQUEST_HANDLERS.put(RegOrAuthClientRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingRegOrAuthClientRequest((RegOrAuthClientRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(GetListRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingGetListRequest((GetListRequest) basicQuery);
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

        REQUEST_HANDLERS.put(ClearFileOutputStreamsRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingClearFileOutputStreamsRequest((ClearFileOutputStreamsRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(GetFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingGetFileRequest((GetFileRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(PermissionToTransferRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingPermissionToTransferRequest((PermissionToTransferRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(PatternMatchingRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingPatternMatchingRequest((PatternMatchingRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(OverwriteFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingOverwriteFileRequest((OverwriteFileRequest) basicQuery);
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        serverLogic.setContext(ctx);
        serverLogic.addConnectionLimit(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyServer.getUserContextRepository().deleteUserContext(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {;
        BasicQuery request = (BasicQuery) msg;

        if (!serverLogic.filterSecurity(request)) return;

        System.out.println(request.getClassName());
        BiConsumer<ServerHandlerLogic, BasicQuery> channelServerHandlerContextConsumer = REQUEST_HANDLERS.get(request.getClass());
        channelServerHandlerContextConsumer.accept(serverLogic, request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
