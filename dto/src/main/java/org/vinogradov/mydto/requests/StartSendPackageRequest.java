package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.FileInfo;
import org.vinogradov.mydto.commonClasses.User;

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
