package org.vinogradov.myclient.clientLogic;

import javafx.application.Platform;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

public class ClientHandlerLogic {

    public static AuthClientRequest getAuthClientRequest(User user) {
        return new AuthClientRequest(user);
    }

    public static RegClientRequest getRegClientRequest(User user) {
        return new RegClientRequest(user);
    }

    public static void getServerMessageReg(RegServerResponse response) {
        if (response.isRegComplete()) Platform.runLater(AlertWindowsClass::showRegComplete);
        else Platform.runLater(AlertWindowsClass::showRegFalse);
    }

    public static void getServerMessageAuth(AuthServerResponse response) {
        if (response.isAuthComplete()) Platform.runLater(AlertWindowsClass::showAuthComplete);
        else Platform.runLater(AlertWindowsClass::showAuthFalse);
    }

}
