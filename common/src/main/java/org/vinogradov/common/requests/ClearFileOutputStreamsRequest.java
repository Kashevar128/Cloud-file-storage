package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class ClearFileOutputStreamsRequest implements BasicQuery {

    User user;

    public ClearFileOutputStreamsRequest(User user) {
        this.user = user;
    }

    @Override
    public String getClassName() {
        return ClearFileOutputStreamsRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }
}
