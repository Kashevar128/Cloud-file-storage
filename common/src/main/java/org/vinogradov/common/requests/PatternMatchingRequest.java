package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.StatusUser;
import org.vinogradov.common.commonClasses.User;

public class PatternMatchingRequest implements BasicQuery {

    private final User user;

    private final StatusUser statusUser;

    public PatternMatchingRequest(User user, StatusUser statusUser) {
        this.user = user;
        this.statusUser = statusUser;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String getClassName() {
        return PatternMatchingRequest.class.getName();
    }
}
