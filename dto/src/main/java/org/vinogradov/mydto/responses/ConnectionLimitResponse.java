package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class ConnectionLimitResponse implements BasicQuery {


    @Override
    public String getType() {
        return "Connection limit";
    }

}
