package org.vinogradov.myclient.clientLogic;

import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;

public class ClientHandlerLogic {

    public static AuthClientRequest getAuthClientRequest(User user) {
        return new AuthClientRequest(user);
    }

    public static RegClientRequest getRegClientRequest(User user) {
        return new RegClientRequest(user);
    }

}
