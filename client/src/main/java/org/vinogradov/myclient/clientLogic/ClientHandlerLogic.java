package org.vinogradov.myclient.clientLogic;

import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

public interface ClientHandlerLogic {

    void getHandingMessageReg(RegServerResponse responseReg);

    void getHandingMessageAuth(AuthServerResponse responseAuth);

    void getHandingMessageList(GetListResponse responseList);

    void getHandingOperationBan();

    void getHandingConnectionLimit();
}
