package org.vinogradov.myclient.clientService;

import org.vinogradov.common.responses.*;

public interface ClientHandlerLogic {

    void getHandingRegOrAuthResponse(RegOrAuthServerResponse responseAuth);

    void getHandingGetListResponse(GetListResponse responseList);

    void getHandingConnectionLimit(ConnectionLimitResponse connectionLimitResponse);

    void getHandingPermissionToTransferResponse(PermissionToTransferResponse permissionToTransferResponse);

    void getHandingMetaDataResponse(MetaDataResponse metaDataResponse);

    void getHandingSendPartFileResponse(SendPartFileResponse sendPartFileResponse);

    void getHandingPatternMatchingResponse(PatternMatchingResponse patternMatchingResponse);

    void getHandingClearClientMapResponse(ClearClientMapResponse clearClientMapResponse);

    void getHandingNotCreateNewPathResponse(NotCreateNewPathResponse notCreateNewPathResponse);

    void getHandingTheUserIsAlreadyLoggedIn(TheUserIsAlreadyLoggedIn theUserIsAlreadyLoggedIn);

    void getHandingOverwriteFileResponse(OverwriteFileResponse overwriteFileResponse);

    void getHandingBanUserResponse(BanUserResponse banUserResponse);
}
