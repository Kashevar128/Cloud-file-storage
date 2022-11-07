package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.requests.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private ServerLogic serverLogic;

    private static final Map<Class<? extends BasicQuery>, BiConsumer<ServerHandlerLogic, BasicQuery>> REQUEST_HANDLERS = new HashMap<>();

    public ServerHandler(ServerLogic serverLogic) {
        this.serverLogic = serverLogic;
        serverLogic.getServerHandlers().add(this);
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

        REQUEST_HANDLERS.put(StartSendPackageRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingStartPackageRequest((StartSendPackageRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(SendPackageRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingSendPackageRequest((SendPackageRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(StopSendPackageRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingStopPackageRequest((StopSendPackageRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(DelFileRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingDelFileRequest((DelFileRequest) basicQuery);
        }));

        REQUEST_HANDLERS.put(CreateNewFolderRequest.class, ((serverHandlerLogic, basicQuery) -> {
            serverHandlerLogic.getHandingCreateNewFolderRequest((CreateNewFolderRequest) basicQuery);
        }));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        serverLogic.addConnectionLimit(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        serverLogic.deleteUserConnection(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        BasicQuery request = (BasicQuery) msg;

        serverLogic.channelCollector(request, ctx);
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
