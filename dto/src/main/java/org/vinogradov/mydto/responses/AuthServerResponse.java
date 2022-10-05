package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;

public class AuthServerResponse implements BasicQuery {

    private boolean authComplete;

    public AuthServerResponse(boolean authComplete) {
        this.authComplete = authComplete;
    }

    @Override
    public String getType() {
        return "Server auth";
    }

    public boolean isAuthComplete() {
        return authComplete;
    }
}