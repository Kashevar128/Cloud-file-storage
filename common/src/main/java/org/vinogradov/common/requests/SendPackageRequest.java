package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class SendPackageRequest implements BasicQuery {

    private String dstPath;

    private byte[] packagePart;

    private User user;

    public SendPackageRequest(String dstPath, byte[] packagePart, User user) {
        this.dstPath = dstPath;
        this.packagePart = packagePart;
        this.user = user;
    }

    @Override
    public String getType() {
        return "Send file";
    }

    public String getDstPath() {
        return dstPath;
    }

    public byte[] getPackagePart() {
        return packagePart;
    }

    @Override
    public User getUser() {
        return user;
    }
}
