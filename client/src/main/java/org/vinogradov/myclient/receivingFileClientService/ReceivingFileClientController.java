package org.vinogradov.myclient.receivingFileClientService;

import org.vinogradov.common.commonClasses.CounterFileSize;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.ReceivingController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ReceivingFileClientController implements ReceivingController {

    private String dstPath;

    private String fileName;

    private Map<Long, FileOutputStream> fileOutputStreamMap;

    private CounterFileSize counterFileSize;

    public ReceivingFileClientController() {
        this.fileOutputStreamMap = new HashMap<>();
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

    public double getRatioCounter() {
        return counterFileSize.getRatio();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CounterFileSize getCounterFileSize() {
        return counterFileSize;
    }

    public String getDstPath() {
        return dstPath;
    }

    public void setDstPath(String dstPath) {
        this.dstPath = dstPath;
    }
}
