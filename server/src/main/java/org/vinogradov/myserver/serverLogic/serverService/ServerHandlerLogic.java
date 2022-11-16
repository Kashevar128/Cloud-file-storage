package org.vinogradov.myserver.serverLogic.serverService;

import org.vinogradov.common.requests.*;

public interface ServerHandlerLogic {

    void sendRegServerResponse(RegClientRequest regClient);

    void sendAuthServerResponse(AuthClientRequest authClient);

    void sendListResponse(GetListRequest listRequest);

    void getHandingDelFileRequest(DelFileRequest delFileRequest);

    void getHandingCreateNewFolderRequest(CreateNewFolderRequest createNewFolderRequest);
}
