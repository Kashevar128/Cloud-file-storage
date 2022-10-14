package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;


public class OperationBanResponse implements BasicQuery {

    @Override
    public String getType() {
        return "operation ban";
    }

}
