package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class BanUserResponse implements BasicQuery {

    @Override
    public String getClassName() {
        return BanUserResponse.class.getName();
    }
}
