package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class ConnectionLimitResponse implements BasicQuery {


    @Override
    public String getClassName() {
        return ConnectionLimitResponse.class.getName();
    }

}
