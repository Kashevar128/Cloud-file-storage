package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.commonClasses.BasicQuery;

public class GetFileRequest implements BasicQuery {



    @Override
    public String getType() {
        return "Get file";
    }
}
