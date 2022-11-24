package org.vinogradov.myserver.serverLogic.DownloadService;

public class CounterFileSize {

    private final long referenceSize;

    private long currentSize;

    public CounterFileSize(long referenceSize) {
        this.referenceSize = referenceSize;
    }

    public void addSize(long size) {
        currentSize += size;
    }

    public boolean getComparisonResult() {
        return referenceSize == currentSize;
    }
}
