package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.commonClasses.BasicQuery;


public class OperationBanResponse implements BasicQuery {

    @Override
    public String getType() {
        return "operation ban";
    }

}
