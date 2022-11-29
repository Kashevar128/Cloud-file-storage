package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class ClearClientMapResponse implements BasicQuery {
    @Override
    public String getClassName() {
        return ClearClientMapResponse.class.getName();
    }
}
