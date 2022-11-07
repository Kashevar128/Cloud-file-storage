package org.vinogradov.common.requests;

import org.vinogradov.common.commonClasses.BasicQuery;

public class GetFileRequest implements BasicQuery {



    @Override
    public String getType() {
        return "Get file";
    }
}
