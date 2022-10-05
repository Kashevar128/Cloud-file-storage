package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;

public class RegServerResponse implements BasicQuery {

    private boolean regComplete;

    public RegServerResponse(boolean regComplete) {
        this.regComplete = regComplete;
    }

    @Override
    public String getType() {
        return "Server reg";
    }

    public boolean isRegComplete() {
        return regComplete;
    }
}
