package org.vinogradov.myclient.downloadService;

import org.vinogradov.common.commonClasses.CounterFileSize;

import java.util.HashMap;
import java.util.Map;

public class SendFilesControllerClient {
    private final GenerateIdFile generateIdFile;
    private Map<Long, String> srcPathsMap;
    private String nameFileOrDirectorySend;
    private String SelectedDstPath;
    private CounterFileSize counterFileSize;

    public SendFilesControllerClient() {
        this.generateIdFile = new GenerateIdFile();
        this.srcPathsMap = new HashMap<>();
    }

    private Long getUniqIdFile() {
        return generateIdFile.getIdFile();
    }

    public Map<Long, String> getMapSrcPaths () {
        return srcPathsMap;
    }

    public Long addNewSrcPath(String srcPath) {
        long id = getUniqIdFile();
        srcPathsMap.put(id, srcPath);
        return id;
    }

    public void clearSrcPathsMap() {
        srcPathsMap = new HashMap<>();
    }

    public void createNewCounterFileSize(long referenceSize) {
        counterFileSize = new CounterFileSize(referenceSize);
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

    public double getRatioCounter() {
        return counterFileSize.getRatio();
    }

    public String getNameFileOrDirectorySend() {
        return nameFileOrDirectorySend;
    }

    public void setNameFileOrDirectorySend(String nameFileOrDirectorySend) {
        this.nameFileOrDirectorySend = nameFileOrDirectorySend;
    }

    public String getSelectedDstPath() {
        return SelectedDstPath;
    }

    public void setSelectedDstPath(String selectedDstPath) {
        this.SelectedDstPath = selectedDstPath;
    }
}
