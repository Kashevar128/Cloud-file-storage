package org.vinogradov.myserver.serverLogic;

import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;

public class ServerHandlerLogic {

    static DataBase dataBase;

    static {
        try {
            dataBase = new DataBaseImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static RegServerResponse getRegServerResponse (RegClientRequest regClient) {
        String passCryptMd5 = DigestUtils.md5Hex(regClient.getUser().getPassword());
        boolean regComplete = dataBase.createUser(regClient.getUser().getNameUser(), passCryptMd5);
        if (regComplete) return new RegServerResponse(true);
        return new RegServerResponse(false);
    }

    static AuthServerResponse getAuthServerResponse (AuthClientRequest authClient) {
        String passCryptMd5 = DigestUtils.md5Hex(authClient.getUser().getPassword());
        boolean authComplete = dataBase.auth(authClient.getUser().getNameUser(), passCryptMd5);
        if (authComplete) return new AuthServerResponse(true);
        return new AuthServerResponse(false);
    }
}
