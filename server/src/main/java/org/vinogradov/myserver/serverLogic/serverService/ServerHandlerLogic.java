package org.vinogradov.myserver.serverLogic.serverService;

import org.vinogradov.mydto.requests.*;

public interface ServerHandlerLogic {

    void sendRegServerResponse(RegClientRequest regClient);

    void sendAuthServerResponse(AuthClientRequest authClient);

    void sendListResponse(GetListRequest listRequest);

    void getHandingStartSendFileRequest(StartSendFileRequest startSendFileRequest);

    void getHandingSendPartFileRequest(SendPartFileRequest sendPartFileRequest);

    void getHandingStopSendFileRequest(StopSendFileRequest stopSendFileRequest);
}
