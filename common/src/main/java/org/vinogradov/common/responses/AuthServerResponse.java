package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.UpdatePanel;
import org.vinogradov.common.commonClasses.User;

import java.nio.file.Path;

public class AuthServerResponse implements BasicQuery {

    private final boolean authComplete;

    private UpdatePanel updatePanel;

    private User user;

    public AuthServerResponse(boolean authComplete, Path path, User user) {
        this.authComplete = authComplete;
        updatePanel = new UpdatePanel(path.toString(),
                HelperMethods.generateFileInfoList(path));
        this.user = user;
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

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }

    @Override
    public User getUser() {
        return user;
    }
}