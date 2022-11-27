package org.vinogradov.myclient.clientService;

import org.vinogradov.common.responses.*;

public interface ClientHandlerLogic {

    void getHandingRegOrAuthResponse(RegOrAuthServerResponse responseAuth);

    void getHandingGetListResponse(GetListResponse responseList);

    void getHandingConnectionLimit();

    void getHandingPermissionToTransferResponse(PermissionToTransferResponse permissionToTransferResponse);

    void getHandingMetaDataResponse(MetaDataResponse metaDataResponse);

    void getHandingSendPartFileResponse(SendPartFileResponse sendPartFileResponse);

    void getHandingPatternMatchingResponse(PatternMatchingResponse patternMatchingResponse);
}
