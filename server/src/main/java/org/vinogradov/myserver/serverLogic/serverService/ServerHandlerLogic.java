package org.vinogradov.myserver.serverLogic.serverService;

import org.vinogradov.common.requests.*;

public interface ServerHandlerLogic {

    void getHandingRegOrAuthClientRequest(RegOrAuthClientRequest authClient);

    void getHandingGetListRequest(GetListRequest listRequest);

    void getHandingDelFileRequest(DelFileRequest delFileRequest);

    void getHandingCreateNewFolderRequest(CreateNewFolderRequest createNewFolderRequest);

    void getHandingMetaDataFileRequest(MetaDataFileRequest metaDataFileRequest);

    void getHandingSendPartFileRequest(SendPartFileRequest sendPartFileRequest);

    void getHandingClearFileOutputStreamsRequest(ClearFileOutputStreamsRequest clearFileOutputStreamsRequest);

    void getHandingGetFileRequest(GetFileRequest getFileRequest);

    void getHandingPermissionToTransferRequest(PermissionToTransferRequest permissionToTransferRequest);

    void getHandingPatternMatchingRequest(PatternMatchingRequest patternMatchingRequest);

    void getHandingOverwriteFileRequest(OverwriteFileRequest overwriteFileRequest);
}
