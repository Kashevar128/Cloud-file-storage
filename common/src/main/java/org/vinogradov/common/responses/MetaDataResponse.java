package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

import java.util.Map;

public class MetaDataResponse implements BasicQuery {

    private final Map<Long, String> dstPaths;

    private final long sizeFile;

    public MetaDataResponse(Map<Long, String> dstPaths, long sizeFile) {
        this.dstPaths = dstPaths;
        this.sizeFile = sizeFile;
    }

    public Map<Long, String> getDstPaths() {
        return dstPaths;
    }

    public long getSizeFile() {
        return sizeFile;
    }

    @Override
    public String getClassName() {
        return MetaDataResponse.class.getName();
    }
}
