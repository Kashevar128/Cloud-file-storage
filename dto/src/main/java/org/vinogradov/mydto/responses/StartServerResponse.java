package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicReqRes;
import org.vinogradov.mydto.User;

public class StartServerResponse implements BasicReqRes {

    @Override
    public String getType() {
        return "Server ready";
    }
}
