package org.vinogradov.myclient.downloadService;

public class GenerateIdFile {

    long idFile = 0L;

    public long getIdFile() {
        idFile++;
        return idFile;
    }
}
