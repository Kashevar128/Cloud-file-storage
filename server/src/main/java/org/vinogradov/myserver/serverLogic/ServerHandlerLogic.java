package org.vinogradov.myserver.serverLogic;

import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.responses.StartServerResponse;

public class ServerHandlerLogic {

    static StartServerResponse getStartServerResponse(BasicReqRes basicReqRes) {
        return new StartServerResponse();
    }
}
