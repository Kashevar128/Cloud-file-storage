package org.vinogradov.common.commonClasses;

import java.io.Serializable;
import java.util.Map;

public class FilePaths implements Serializable {

    private final String fileName;

    private final Map<Long, String> paths;

    public FilePaths(String fileName, Map<Long, String> filePaths) {
        this.fileName = fileName;
        this.paths = filePaths;
    }

    public String getFileName() {
        return fileName;
    }

    public Map<Long, String> getPaths() {
        return paths;
    }
}
