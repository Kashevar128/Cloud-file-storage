package org.vinogradov.myclient.receivingFileClientService;

import org.vinogradov.common.commonClasses.CounterFileSize;
import org.vinogradov.common.commonClasses.HelperMethods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReceivingFileClientController {

    private String dstPath;

    private Map<Long, FileOutputStream> fileOutputStreamMap;

    private CounterFileSize counterFileSize;

    public ReceivingFileClientController() {
        this.fileOutputStreamMap = new HashMap<>();
    }

    public String getDstPath() {
        return dstPath;
    }

    public void setDstPath(String dstPath) {
        this.dstPath = dstPath;
    }

    public void addFileOutputStreamMap(Map<Long, String> dstPathsMap) {
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

    public void createCounterFileSize(long referenceSize) {
        counterFileSize = new CounterFileSize(referenceSize);
    }

    public void addSizePartInCounter(long sizePart) {
        counterFileSize.addSize(sizePart);
    }

    public boolean sizeFileCheck() {
        return counterFileSize.getComparisonResult();
    }
}
