package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.FileInfo;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.UpdatePanel;

import java.nio.file.Path;

public class GetListResponse implements BasicQuery {

    private final UpdatePanel updatePanel;

    public GetListResponse(Path path) {
        updatePanel = new UpdatePanel(path.toString(),
                HelperMethods.generateFileInfoList(path));
    }

    @Override
    public String getType() {
        return "this new list";
    }

    public UpdatePanel getUpdatePanel() {
        return updatePanel;
    }
}
