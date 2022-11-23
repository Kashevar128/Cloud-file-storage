package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class MetaDataFileResponse implements BasicQuery {

    private final String fileOrDirectoryName;

    private final boolean allowTransmission;

    public MetaDataFileResponse(String fileOrDirectoryName, boolean allowTransmission) {
        this.fileOrDirectoryName = fileOrDirectoryName;
        this.allowTransmission = allowTransmission;
    }

    public String getFileOrDirectoryName() {
        return fileOrDirectoryName;
    }

    public boolean isAllowTransmission() {
        return allowTransmission;
    }

    @Override
    public String getType() {
        return MetaDataFileResponse.class.toString();
    }
}
