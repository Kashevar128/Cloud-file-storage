package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.requests.*;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

public interface ServerHandlerLogic {

    void sendRegServerResponse(RegClientRequest regClient);

    void sendAuthServerResponse(AuthClientRequest authClient);

    void sendListResponse(GetListRequest listRequest);

    void getHandingSendFileRequest(SendFileRequest sendFileRequest);
}
