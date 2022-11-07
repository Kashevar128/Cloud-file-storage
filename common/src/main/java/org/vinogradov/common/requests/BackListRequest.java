package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

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
