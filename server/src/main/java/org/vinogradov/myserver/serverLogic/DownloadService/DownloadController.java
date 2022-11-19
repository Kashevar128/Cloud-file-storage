package org.vinogradov.myserver.serverLogic.DownloadService;

import org.vinogradov.common.commonClasses.HelperMethods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DownloadController {
    Map<String, Map<Long, FileOutputStream>> fileOutputStreamMap;
    Map<String, CounterFileSize> counterFileSizeMap;
    Map<String, String> parentDirectoryPathsMap;

    public DownloadController() {
        this.fileOutputStreamMap = new HashMap<>();
        this.counterFileSizeMap = new HashMap<>();
        this.parentDirectoryPathsMap = new HashMap<>();
    }

    public void createNewFileOutputStreams(String fileName, Map<Long, String> paths) {
        ;
        fileOutputStreamMap.put(fileName, new HashMap<>());
        for (Map.Entry<Long, String> entry : paths.entrySet()) {
            HelperMethods.createNewDirectoryRecursion(Paths.get(entry.getValue()).getParent());
            FileOutputStream fileOutputStream =
                    HelperMethods.generateFileOutputStream(entry.getValue());
            fileOutputStreamMap.get(fileName).put(entry.getKey(), fileOutputStream);
        }
    }

    public void createCounterFileSize(String fileName, long referenceSize) {
        CounterFileSize counterFileSize = new CounterFileSize(referenceSize);
        counterFileSizeMap.put(fileName, counterFileSize);
    }

    public void addSizeBytesInCounter(String fileName, long sizePart) {
        CounterFileSize counterFileSize = counterFileSizeMap.get(fileName);
        counterFileSize.addSize(sizePart);
    }

    public void addBytesInFileOutputStream(String fileName, Long id, byte[] bytes) {
        FileOutputStream fileOutputStream = fileOutputStreamMap.get(fileName).get(id);
        try {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean sizeFileCheck(String fileName) {
        CounterFileSize counterFileSize = counterFileSizeMap.get(fileName);
        return counterFileSize.getComparisonResult();
    }

    public void addParentDirectoryPath(String fileName, String dstPath) {
        parentDirectoryPathsMap.put(fileName, dstPath);
    }

    public String getParentDirectoryPath(String fileName) {
        return parentDirectoryPathsMap.get(fileName);
    }

    public void completingTheFileUploader(String fileName) {
        closeAllFileOutputStreamInDirectory(fileName);
        fileOutputStreamMap.remove(fileName);
        counterFileSizeMap.remove(fileName);
        parentDirectoryPathsMap.remove(fileName);
    }

    private void closeAllFileOutputStreamInDirectory(String fileName) {
        Map<Long, FileOutputStream> longFileOutputStreamMap = fileOutputStreamMap.get(fileName);
        for (Map.Entry<Long, FileOutputStream> entry : longFileOutputStreamMap.entrySet()) {
            try {
                entry.getValue().close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
