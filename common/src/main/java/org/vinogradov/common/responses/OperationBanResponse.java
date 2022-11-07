package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;


public class OperationBanResponse implements BasicQuery {

    @Override
    public String getType() {
        return "operation ban";
    }

}
