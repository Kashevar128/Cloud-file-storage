package org.vinogradov.common.commonClasses;

public class GenerateIdFile {

    long idFile = 0L;

    public long getIdFile() {
        idFile++;
        return idFile;
    }
}
