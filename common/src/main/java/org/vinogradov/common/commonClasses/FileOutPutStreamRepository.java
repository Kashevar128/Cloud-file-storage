package org.vinogradov.common.commonClasses;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileOutPutStreamRepository {

    private final String fileOrDirectoryName;

    private final Map<Long, FileOutputStream> fileOutputStreamMap;

    public FileOutPutStreamRepository(String fileOrDirectoryName) {
        this.fileOrDirectoryName = fileOrDirectoryName;
        this.fileOutputStreamMap = new HashMap<>();
    }

    public String getFileOrDirectoryName() {
        return fileOrDirectoryName;
    }

    public Map<Long, FileOutputStream> getFileOutputStreamMap() {
        return fileOutputStreamMap;
    }

    public void createNewFileOutputStream(Long id, FileOutputStream fileOutputStream) {
        fileOutputStreamMap.put(id, fileOutputStream);
    }
}
