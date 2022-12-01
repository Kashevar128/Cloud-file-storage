package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class GetListRequest implements BasicQuery {
    User user;
    String path;

    @Override
    public String getClassName() {
        return GetListRequest.class.getName();
    }

    public GetListRequest(User user, String path) {
        this.user = user;
        this.path = path;
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getPath() {
        return path;
    }
}
