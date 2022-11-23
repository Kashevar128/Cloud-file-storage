package org.vinogradov.common.commonClasses;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FilePaths implements Serializable {

    private final String fileOrDirectoryName;

    private final Map<Long, String> paths;

    public FilePaths(String fileOrDirectoryName) {
        this.fileOrDirectoryName = fileOrDirectoryName;
        this.paths = new HashMap<>();
    }

    public String getFileOrDirectoryName() {
        return fileOrDirectoryName;
    }

    public Map<Long, String> getPaths() {
        return paths;
    }

    public String getFilePath(long idFile) {
        return paths.get(idFile);
    }

    public void addStringPath(Long idFile, String srcPath) {
        paths.put(idFile, srcPath);
    }
}
