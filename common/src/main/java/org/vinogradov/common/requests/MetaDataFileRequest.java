package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

import java.util.Map;

public class MetaDataFileRequest implements BasicQuery {

    private final User user;

    private final Map<Long, String> dstPathsMap;

    private final String parentDirectory;

    private final long sizeFile;

    public MetaDataFileRequest(User user, Map<Long, String> dstPathsMap, String parentDirectory, long sizeFile) {
        this.user = user;
        this.dstPathsMap = dstPathsMap;
        this.parentDirectory = parentDirectory;
        this.sizeFile = sizeFile;
    }

    @Override
    public String getClassName() {
        return MetaDataFileRequest.class.getName();
    }

    @Override
    public User getUser() {
        return user;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public Map<Long, String> getDstPathsMap() {
        return dstPathsMap;
    }
}
