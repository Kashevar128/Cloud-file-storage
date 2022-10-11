package org.vinogradov.myclient.clientLogic;

import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

public interface ClientHandlerLogic {

    void getResultMessageReg(RegServerResponse responseReg);

    void getResultMessageAuth(AuthServerResponse responseAuth);
}
