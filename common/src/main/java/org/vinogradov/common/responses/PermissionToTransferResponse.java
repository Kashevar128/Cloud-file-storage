package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class PermissionToTransferResponse implements BasicQuery {

    private final boolean allowTransmission;

    public PermissionToTransferResponse(boolean allowTransmission) {
        this.allowTransmission = allowTransmission;
    }

    public boolean isAllowTransmission() {
        return allowTransmission;
    }

    @Override
    public String getClassName() {
        return PermissionToTransferResponse.class.getName();
    }
}
