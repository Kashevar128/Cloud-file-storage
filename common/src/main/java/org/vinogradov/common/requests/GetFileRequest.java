package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.FileInfo;
import org.vinogradov.common.commonClasses.User;

public class GetFileRequest implements BasicQuery {

    private final User user;

    private final FileInfo.FileType fileType;

    private final String srcPath;

    private final String dstPath;

    public GetFileRequest(User user, FileInfo.FileType fileType, String srcPath, String dstPath) {
        this.user = user;
        this.fileType = fileType;
        this.srcPath = srcPath;
        this.dstPath = dstPath;
    }

    @Override
    public String getClassName() {
        return GetFileRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }

    public FileInfo.FileType getFileType() {
        return fileType;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public String getDstPath() {
        return dstPath;
    }
}
