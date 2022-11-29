package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class BackListRequest implements BasicQuery {

    User user;

    public BackListRequest(User user) {
        this.user = user;
    }

    @Override
    public String getClassName() {
        return BackListRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }
}
