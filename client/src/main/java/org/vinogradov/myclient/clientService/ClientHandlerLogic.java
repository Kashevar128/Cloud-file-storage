package org.vinogradov.myclient.clientService;

import org.vinogradov.common.responses.AuthServerResponse;
import org.vinogradov.common.responses.GetListResponse;
import org.vinogradov.common.responses.MetaDataFileResponse;
import org.vinogradov.common.responses.RegServerResponse;

public interface ClientHandlerLogic {

    void getHandingMessageReg(RegServerResponse responseReg);

    void getHandingMessageAuth(AuthServerResponse responseAuth);

    void getHandingMessageList(GetListResponse responseList);

    void getHandingConnectionLimit();

    void getHandingMetaDataResponse(MetaDataFileResponse metaDataFileResponse);
}
