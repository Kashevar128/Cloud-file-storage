package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.UpdatePanel;

import java.nio.file.Path;

public class GetListResponse implements BasicQuery {

    private final UpdatePanel updatePanel;

    public GetListResponse(String clientPath, Path serverPath) {
        updatePanel = new UpdatePanel(clientPath,
                HelperMethods.generateFileInfoList(serverPath));
    }

    @Override
    public String getType() {
        return "this new list";
    }

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }
}
