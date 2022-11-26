package org.vinogradov.common.requests;


import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class PermissionToTransferRequest implements BasicQuery {

    private final User user;

    private final boolean allowTransmission;

    public PermissionToTransferRequest(User user, boolean allowTransmission) {
        this.user = user;
        this.allowTransmission = allowTransmission;
    }

    @Override
    public String getType() {
        return PermissionToTransferRequest.class.toString();
    }

    @Override
    public User getUser() {
        return user;
    }

    public boolean isAllowTransmission() {
        return allowTransmission;
    }
}
