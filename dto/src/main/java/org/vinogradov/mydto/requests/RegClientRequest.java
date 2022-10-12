package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class RegClientRequest implements BasicQuery {

    private User user;

    public RegClientRequest(User user) {
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
