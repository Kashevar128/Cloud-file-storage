package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class DelFileRequest implements BasicQuery {

    private final User user;

    private final String delFilePath;

    public DelFileRequest(User user, String delFilePath) {
        this.user = user;
        this.delFilePath = delFilePath;
    }

    @Override
    public String getClassName() {
        return DelFileRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getDelFilePath() {
        return delFilePath;
    }
}
