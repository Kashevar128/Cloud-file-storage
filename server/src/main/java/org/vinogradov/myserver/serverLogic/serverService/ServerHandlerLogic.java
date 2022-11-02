package org.vinogradov.myserver.serverLogic.serverService;

import org.vinogradov.mydto.requests.*;

public interface ServerHandlerLogic {

    void sendRegServerResponse(RegClientRequest regClient);

    void sendAuthServerResponse(AuthClientRequest authClient);

    void sendListResponse(GetListRequest listRequest);

    void getHandingStartPackageRequest(StartSendPackageRequest startSendPackageRequest);

    void getHandingSendPackageRequest(SendPackageRequest sendPackageRequest);

    void getHandingStopPackageRequest(StopSendPackageRequest stopSendPackageRequest);
}
