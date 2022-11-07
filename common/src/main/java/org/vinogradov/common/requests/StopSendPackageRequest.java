package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

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
