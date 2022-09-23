package org.vinogradov.myclient.clientLogic;

import org.vinogradov.mydto.requests.StartClientRequest;

public class ClientHandlerLogic {

    static StartClientRequest getStartClientRequest() {
        return new StartClientRequest();
    }
}
