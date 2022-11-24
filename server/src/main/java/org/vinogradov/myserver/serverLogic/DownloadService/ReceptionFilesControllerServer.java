package org.vinogradov.myserver.serverLogic.DownloadService;
import org.vinogradov.common.commonClasses.CounterFileSize;
import org.vinogradov.common.commonClasses.HelperMethods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReceptionFilesControllerServer {
    Map<Long, FileOutputStream> fileOutputStreamMap;
    CounterFileSize counterFileSize;
    String parentDirectoryFile;

    public ReceptionFilesControllerServer() {
        fileOutputStreamMap = new HashMap<>();
    }

    public void addFileOutputStreamRepository(Map<Long,String> dstPathsMap) {
        for (Map.Entry<Long, String> entry : dstPathsMap.entrySet()) {
            HelperMethods.createNewDirectoryRecursion(Paths.get(entry.getValue()).getParent());
            FileOutputStream fileOutputStream =
                    HelperMethods.generateFileOutputStream(entry.getValue());
            fileOutputStreamMap.put(entry.getKey(), fileOutputStream);
        }
    }

    public void addBytesInFileOutputStream(Long id, byte[] bytes) {
            FileOutputStream fileOutputStream = fileOutputStreamMap.get(id);
            try {
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    public void createCounterFileSize(long referenceSize) {
        counterFileSize = new CounterFileSize(referenceSize);
    }

    public void addSizePartInCounter(long sizePart) {
            counterFileSize.addSize(sizePart);
    }

    public boolean sizeFileCheck() {
            return counterFileSize.getComparisonResult();
    }

    public void addParentDirectoryPath(String dstPath) {
        parentDirectoryFile = dstPath;
    }

    public String getParentDirectoryPath() {
        return parentDirectoryFile;
    }

    public void closeAllFileOutputStreamInDirectory() {
            fileOutputStreamMap.forEach((key, value) -> {
                try {
                    value.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fileOutputStreamMap = new HashMap<>();
    }
}
