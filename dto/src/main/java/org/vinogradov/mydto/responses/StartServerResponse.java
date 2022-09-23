package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicReqRes;

public class StartServerResponse implements BasicReqRes {

    @Override
    public String getType() {
        return "Server ready";
    }
}
