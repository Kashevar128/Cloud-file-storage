package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

import java.util.Map;

public class MetaDataFileRequest implements BasicQuery {

    private final User user;

    private final String fileName;

    private final Map<Long, String> dstPaths;

    private final String parentDirectory;

    private final long sizeFile;

    public MetaDataFileRequest(User user, String fileName, Map<Long, String> dstPaths, String parentDirectory, long sizeFile) {
        this.user = user;
        this.fileName = fileName;
        this.dstPaths = dstPaths;
        this.parentDirectory = parentDirectory;
        this.sizeFile = sizeFile;
    }

    @Override
    public String getType() {
        return MetaDataFileRequest.class.toString();
    }

    @Override
    public User getUser() {
        return user;
    }

    public String getFileName() {
        return fileName;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    public String getParentDirectory() {
        return parentDirectory;
    }

    public Map<Long, String> getDstPaths() {
        return dstPaths;
    }
}
