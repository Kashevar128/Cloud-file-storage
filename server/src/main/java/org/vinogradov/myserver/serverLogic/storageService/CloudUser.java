package org.vinogradov.myserver.serverLogic.storageService;

public class CloudUser {

    private final String nameUser;

    private long maxSize;

    private long sizeCloud;

    public CloudUser(String nameUser, long maxSize, long sizeCloud) {
        this.nameUser = nameUser;
        this.maxSize = maxSize;
        this.sizeCloud = sizeCloud;
    }

    public void addSize(long size) {
        sizeCloud += size;
    }

    public void takeAwaySize(long size) {
        sizeCloud -= size;
    }

    public boolean predictTheSize(long size) {
        long copySizeCloud = sizeCloud;
        copySizeCloud += size;
        return copySizeCloud >= maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public String getNameUser() {
        return nameUser;
    }

    public long getSizeCloud() {
        return sizeCloud;
    }
}
