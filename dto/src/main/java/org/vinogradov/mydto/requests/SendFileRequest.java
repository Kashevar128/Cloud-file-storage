package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.FileInfo;
import org.vinogradov.mydto.User;

public class SendFileRequest implements BasicQuery {

    private String dstPath;

    private FileInfo fileInfo;

    private byte[] packageByte;

    private User user;

    public SendFileRequest(String dstPath, FileInfo fileInfo, byte[] packageByte, User user) {
        this.dstPath = dstPath;
        this.fileInfo = fileInfo;
        this.packageByte = packageByte;
        this.user = user;
    }

    @Override
    public String getType() {
        return "Send file";
    }

    public String getDstPath() {
        return dstPath;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public byte[] getPackageByte() {
        return packageByte;
    }

    @Override
    public User getUser() {
        return user;
    }
}
