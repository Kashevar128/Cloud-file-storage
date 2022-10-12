package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class GetListRequest implements BasicQuery {
    User user;
    String path;

    @Override
    public String getType() {
        return "Get list...";
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
