package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class MetaDataFileResponse implements BasicQuery {

    private final String fileName;

    private final boolean allowTransmission;

    public MetaDataFileResponse(String fileName, boolean allowTransmission) {
        this.fileName = fileName;
        this.allowTransmission = allowTransmission;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isAllowTransmission() {
        return allowTransmission;
    }

    @Override
    public String getType() {
        return MetaDataFileResponse.class.toString();
    }
}
