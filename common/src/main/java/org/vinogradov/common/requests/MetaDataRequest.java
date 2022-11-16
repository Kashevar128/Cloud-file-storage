package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

import java.util.List;

public class MetaDataRequest implements BasicQuery {

    private final User user;

    private final List<String> dstPaths;

    private final String fileName;

    private final long referenceSize;

    public MetaDataRequest(User user, List<String> dstPaths, String fileName, long referenceSize) {
        this.user = user;
        this.dstPaths = dstPaths;
        this.fileName = fileName;
        this.referenceSize = referenceSize;
    }

    @Override
    public String getType() {
        return MetaDataRequest.class.toString();
    }

    @Override
    public User getUser() {
        return user;
    }
}
