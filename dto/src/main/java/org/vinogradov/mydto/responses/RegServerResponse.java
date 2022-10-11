package org.vinogradov.mydto.responses;

import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;

import java.util.List;

public class RegServerResponse implements BasicQuery {

    private boolean regComplete;

    private User user;

    private List<String> startList;

    public RegServerResponse(boolean regComplete, User user, List<String> startList) {
        this.regComplete = regComplete;
        this.user = user;
        this.startList = startList;
    }

    public RegServerResponse(boolean regComplete){
        this.regComplete = regComplete;
    }

    @Override
    public String getType() {
        return "Server reg";
    }

    public boolean isRegComplete() {
        return regComplete;
    }

    public User getUser() {
        return user;
    }

    public List<String> getStartList() {
        return startList;
    }
}
