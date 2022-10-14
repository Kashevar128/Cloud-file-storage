package org.vinogradov.mydto.requests;

import org.vinogradov.mydto.BasicQuery;

public class GetFileRequest implements BasicQuery {



    @Override
    public String getType() {
        return "Get file";
    }
}
