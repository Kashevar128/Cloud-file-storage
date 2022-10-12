package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class AuthClientRequest implements BasicQuery {

    private User user;

    public AuthClientRequest(User user) {
        this.user = user;
    }

    @Override
    public String getType() {
        return "Client connect: " + user.getNameUser();
    }

    @Override
    public User getUser() {
        return user;
    }
}
