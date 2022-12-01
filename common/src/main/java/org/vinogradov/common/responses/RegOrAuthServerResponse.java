package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.*;

import java.nio.file.Path;

public class RegOrAuthServerResponse implements BasicQuery {

    private final boolean regOrAuthComplete;

    private final UpdatePanel updatePanel;

    private final StatusUser statusUser;

    private final User user;

    public RegOrAuthServerResponse(boolean regOrAuthComplete, String pathEdit, Path path, StatusUser statusUser , User user) {
        this.regOrAuthComplete = regOrAuthComplete;
        this.updatePanel = new UpdatePanel(pathEdit,
                HelperMethods.generateFileInfoList(path));
        this.statusUser = statusUser;
        this.user = user;
    }

    @Override
    public String getClassName() {
        return RegOrAuthServerResponse.class.getName();
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