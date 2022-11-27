package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.User;

public class StopTransmissionRequest implements BasicQuery {

    User user;

    @Override
    public String getType() {
        return StopTransmissionRequest.class.toString();
    }

    @Override
    public User getUser() {
        return user;
    }
}
