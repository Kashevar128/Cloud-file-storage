package org.vinogradov.myserver.serverLogic.receivingFileServerService;

import org.vinogradov.common.commonClasses.CounterFileSize;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.ReceivingController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReceivingFileServerController implements ReceivingController {
    Map<Long, FileOutputStream> fileOutputStreamMap;
    CounterFileSize counterFileSize;
    String parentDirectoryFile;

    public ReceivingFileServerController() {
        fileOutputStreamMap = new HashMap<>();
    }

    @Override
    public void addFileOutputStreamMap(Map<Long, String> dstPathsMap) {
        for (Map.Entry<Long, String> entry : dstPathsMap.entrySet()) {
            HelperMethods.createNewDirectoryRecursion(Paths.get(entry.getValue()).getParent());
            FileOutputStream fileOutputStream =
                    HelperMethods.generateFileOutputStream(entry.getValue());
            fileOutputStreamMap.put(entry.getKey(), fileOutputStream);
        }
    }

    @Override
    public void addBytesInFileOutputStream(Long id, byte[] bytes) {
        FileOutputStream fileOutputStream = fileOutputStreamMap.get(id);
        try {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createCounterFileSize(long referenceSize) {
        counterFileSize = new CounterFileSize(referenceSize);
    }

    @Override
    public void addSizePartInCounter(long sizePart) {
        counterFileSize.addSize(sizePart);
    }

    @Override
    public boolean sizeFileCheck() {
        return counterFileSize.getComparisonResult();
    }

    @Override
    public void closeAllFileOutputStreams() {
        if (fileOutputStreamMap.isEmpty()) return;
        fileOutputStreamMap.forEach((key, value) -> {
            try {
                value.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fileOutputStreamMap = new HashMap<>();
    }

    public void addParentDirectoryPath(String dstPath) {
        parentDirectoryFile = dstPath;
    }

    public String getParentDirectoryPath() {
        return parentDirectoryFile;
    }
}
