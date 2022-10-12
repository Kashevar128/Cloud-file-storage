package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class BackListRequest implements BasicQuery {

    User user;

    public BackListRequest(User user) {
        this.user = user;
    }

    @Override
    public String getType() {
        return "Back list";
    }

    @Override
    public User getUser() {
        return user;
    }
}
