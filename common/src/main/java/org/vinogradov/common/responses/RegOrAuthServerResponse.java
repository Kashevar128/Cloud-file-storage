package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.*;

import java.nio.file.Path;

public class RegOrAuthServerResponse implements BasicQuery {

    private final boolean regOrAuthComplete;

    private final UpdatePanel updatePanel;

    private final StatusUser statusUser;

    private final User user;

    public RegOrAuthServerResponse(boolean regOrAuthComplete, Path path,StatusUser statusUser , User user) {
        this.regOrAuthComplete = regOrAuthComplete;
        this.updatePanel = new UpdatePanel(path.toString(),
                HelperMethods.generateFileInfoList(path));
        this.statusUser = statusUser;
        this.user = user;
    }

    @Override
    public String getType() {
        return "Server auth";
    }

    public boolean isRegOrAuthComplete() {
        return regOrAuthComplete;
    }

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }

    public StatusUser getStatusUser() {
        return statusUser;
    }

    @Override
    public User getUser() {
        return user;
    }
}