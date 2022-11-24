package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class MetaDataFileResponse implements BasicQuery {

    private final boolean allowTransmission;

    public MetaDataFileResponse(boolean allowTransmission) {
        this.allowTransmission = allowTransmission;
    }

    public boolean isAllowTransmission() {
        return allowTransmission;
    }

    @Override
    public String getType() {
        return MetaDataFileResponse.class.toString();
    }
}
