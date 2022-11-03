package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;

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
