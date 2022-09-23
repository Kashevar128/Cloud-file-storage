package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.User;

public class StartClientRequest implements BasicReqRes {

    private User user;

//    public StartClientRequest(User user) {
//        this.user = user;
//    }

    @Override
    public String getType() {
        return "New client";
    }

    public User getUser() {
        return user;
    }
}
