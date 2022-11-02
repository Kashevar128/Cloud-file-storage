package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;

public class StopSendPackageRequest implements BasicQuery {

    String dstPath;

    User user;

    public StopSendPackageRequest(String dstPath, User user) {
        this.user = user;
        this.dstPath = dstPath;
    }

    @Override
    public String getType() {
        return "Файл полностью передан";
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getDstPath() {
        return dstPath;
    }
}
