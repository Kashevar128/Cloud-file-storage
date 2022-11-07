package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class StartSendPackageRequest implements BasicQuery {

    private String pathFile;

    private User user;

    public StartSendPackageRequest(String pathFile, User user) {
        this.pathFile = pathFile;
        this.user = user;
    }

    @Override
    public String getType() {
        return "Начинается загрузка файла";
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getPathFile() {
        return pathFile;
    }
}
