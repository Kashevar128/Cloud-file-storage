package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.User;

import java.nio.file.Path;
import java.util.List;

public class AuthServerResponse implements BasicQuery {

    private boolean authComplete;

    private User user;

    private List<String> startList;

    public AuthServerResponse(boolean authComplete, User user, Path path) {
        this.authComplete = authComplete;
        this.user = user;
        this.startList = HelperMethods.generateStringList(path);
    }

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

    @Override
    public User getUser() {
        return user;
    }

    public List<String> getStartList() {
        return startList;
    }
}