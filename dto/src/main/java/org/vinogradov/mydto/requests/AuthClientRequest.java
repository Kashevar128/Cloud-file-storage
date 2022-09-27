package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.User;

public class AuthClientRequest implements BasicReqRes {

    private User user;

    public AuthClientRequest(User user) {
        this.user = user;
    }

    @Override
    public String getType() {
        return "Client connect: " + user.getNameUser();
    }

    public User getUser() {
        return user;
    }
}
