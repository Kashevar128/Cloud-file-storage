package org.vinogradov.myserver.serverLogic.DownloadService;

public class CounterFileSize {

    private final String fileOrDirectoryName;

    private final long referenceSize;

    private long currentSize;

    public CounterFileSize(String fileOrDirectoryName, long referenceSize) {
        this.fileOrDirectoryName = fileOrDirectoryName;
        this.referenceSize = referenceSize;
    }

    public void addSize(long size) {
        currentSize += size;
    }

    public boolean getComparisonResult() {
        return referenceSize == currentSize;
    }

    public String getFileOrDirectoryName() {
        return fileOrDirectoryName;
    }
}
