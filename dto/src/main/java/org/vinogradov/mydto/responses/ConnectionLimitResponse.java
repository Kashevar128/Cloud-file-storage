package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

public class ConnectionLimitResponse implements BasicQuery {


    @Override
    public String getType() {
        return "Connection limit";
    }

}
