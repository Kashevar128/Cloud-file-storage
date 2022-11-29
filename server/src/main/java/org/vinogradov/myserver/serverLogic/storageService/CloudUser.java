package org.vinogradov.myserver.serverLogic.storageService;

import org.vinogradov.common.commonClasses.Constants;

public class CloudUser {

    private final String pathCloud;

    private final long maxSize;

    private long sizeCloud;

    public CloudUser(String pathCloud) {
        this.pathCloud = pathCloud;
        this.maxSize = Constants.GB_10;
        this.sizeCloud = 0;
    }

    public void addSize(long size) {
        sizeCloud += size;
    }

    public boolean predictTheSize(long size) {
        long copySizeCloud = sizeCloud;
        copySizeCloud += size;
        return copySizeCloud >= maxSize;
    }

    public String getPathCloud() {
        return pathCloud;
    }

    public long getSizeCloud() {
        return sizeCloud;
    }
}
