package org.vinogradov.common.responses;

import org.vinogradov.common.commonClasses.BasicQuery;

public class OverwriteFileResponse implements BasicQuery {

    private final boolean existsFile;

    public OverwriteFileResponse(boolean existsFile) {
        this.existsFile = existsFile;
    }

    public boolean isExistsFile() {
        return existsFile;
    }

    @Override
    public String getClassName() {
        return OverwriteFileResponse.class.getName();
    }
}
