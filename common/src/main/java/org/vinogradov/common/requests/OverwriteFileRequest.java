package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class OverwriteFileRequest implements BasicQuery {

    private final User user;

    private final String dstPath;

    public OverwriteFileRequest(User user, String dstPath) {
        this.user = user;
        this.dstPath = dstPath;
    }

    @Override
    public String getClassName() {
        return OverwriteFileRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getDstPath() {
        return dstPath;
    }
}
