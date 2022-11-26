package org.vinogradov.myclient.clientService;

import org.vinogradov.common.responses.*;

public interface ClientHandlerLogic {

    void getHandingMessageReg(RegServerResponse responseReg);

    void getHandingMessageAuth(AuthServerResponse responseAuth);

    void getHandingMessageList(GetListResponse responseList);

    void getHandingConnectionLimit();

    void getHandingPermissionToTransferResponse(PermissionToTransferResponse permissionToTransferResponse);

    void getHandingMetaDataResponse(MetaDataResponse metaDataResponse);

    void getHandingSendPartFileResponse(SendPartFileResponse sendPartFileResponse);
}
