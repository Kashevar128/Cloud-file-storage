package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.User;

import java.nio.file.Path;
import java.util.List;

public class RegServerResponse implements BasicQuery {

    private boolean regComplete;

    private User user;

    private List<String> startList;

    public RegServerResponse(boolean regComplete, User user, Path path) {
        this.regComplete = regComplete;
        this.user = user;
        this.startList = HelperMethods.generateStringList(path);
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

    @Override
    public User getUser() {
        return user;
    }

    public List<String> getStartList() {
        return startList;
    }
}
