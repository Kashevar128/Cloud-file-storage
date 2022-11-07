package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class DelFileRequest implements BasicQuery {

    User user;

    String delFilePath;

    public DelFileRequest(User user, String delFilePath) {
        this.user = user;
        this.delFilePath = delFilePath;
    }

    @Override
    public String getType() {
        return "Удаление файла";
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getDelFilePath() {
        return delFilePath;
    }
}
