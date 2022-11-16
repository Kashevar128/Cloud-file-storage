package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.*;

import java.nio.file.Path;
import java.util.List;

public class RegServerResponse implements BasicQuery {

    private final boolean regComplete;

    private UpdatePanel updatePanel;

    private User user;

    public RegServerResponse(boolean regComplete ,Path path, User user) {
        this.regComplete = regComplete;
        updatePanel = new UpdatePanel(path.toString(),
                HelperMethods.generateFileInfoList(path));
        this.user = user;
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

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }

    @Override
    public User getUser() {
        return user;
    }
}
