package org.vinogradov.myserver.serverLogic.DownloadService;

import org.vinogradov.common.commonClasses.FileOutPutStreamRepository;
import org.vinogradov.common.commonClasses.FilePaths;
import org.vinogradov.common.commonClasses.HelperMethods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

public class ReceptionFilesControllerServer {
    FileOutPutStreamRepository fileOutPutStreamRepository;
    CounterFileSize counterFileSize;
    String parentDirectoryFile;

    public void createNewFileOutputStreamRepository(String fileOrDirectoryName, FilePaths filePaths) {
        fileOutPutStreamRepository = new FileOutPutStreamRepository(fileOrDirectoryName);
        Map<Long, String> dstPaths = filePaths.getPaths();
        for (Map.Entry<Long, String> entry : dstPaths.entrySet()) {
            HelperMethods.createNewDirectoryRecursion(Paths.get(entry.getValue()).getParent());
            FileOutputStream fileOutputStream =
                    HelperMethods.generateFileOutputStream(entry.getValue());
            fileOutPutStreamRepository.createNewFileOutputStream(entry.getKey(), fileOutputStream);
        }
    }

    public void addBytesInFileOutputStream(String fileName, Long id, byte[] bytes) {
        if (fileOutPutStreamRepository.getFileOrDirectoryName().equals(fileName)) {
            Map<Long, FileOutputStream> fileOutputStreamMap = fileOutPutStreamRepository.getFileOutputStreamMap();
            FileOutputStream fileOutputStream = fileOutputStreamMap.get(id);
            try {
                fileOutputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else throw new RuntimeException("Репозиторий FileOutputStreams пуст.");
    }

    public void createCounterFileSize(String fileOrDirectoryName, long referenceSize) {
        counterFileSize = new CounterFileSize(fileOrDirectoryName, referenceSize);
    }

    public void addSizePartInCounter(String fileOrDirectoryName, long sizePart) {
        if (counterFileSize.getFileOrDirectoryName().equals(fileOrDirectoryName)) {
            counterFileSize.addSize(sizePart);
        } else throw new RuntimeException("Счетчик размера файла пуст.");
    }

    public boolean sizeFileCheck(String fileName) {
        if (counterFileSize.getFileOrDirectoryName().equals(fileName)) {
            return counterFileSize.getComparisonResult();
        } else throw new RuntimeException("Счетчик размера файла пуст.");
    }

    public void addParentDirectoryPath(String dstPath) {
        parentDirectoryFile = dstPath;
    }

    public String getParentDirectoryPath() {
        if (parentDirectoryFile != null) return parentDirectoryFile;
        else throw new RuntimeException("Родительский путь файла пуст.");
    }

    public void closeAllFileOutputStreamInDirectory(String fileName) {
        if (fileOutPutStreamRepository.getFileOrDirectoryName().equals(fileName)) {
            Map<Long, FileOutputStream> FileOutputStreamMap = fileOutPutStreamRepository.getFileOutputStreamMap();
            for (Map.Entry<Long, FileOutputStream> entry : FileOutputStreamMap.entrySet()) {
                try {
                    entry.getValue().close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else throw new RuntimeException("Репозиторий FileOutputStreams пуст.");
    }
}
