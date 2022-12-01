package org.vinogradov.common.commonClasses;

import java.io.Serializable;
import java.util.List;

public class UpdatePanel implements Serializable{

    String path;

    List<FileInfo> listInfo;

    public UpdatePanel(String path, List<FileInfo> listInfo) {
        this.path = path;
        this.listInfo = listInfo;
    }

    public String getPath() {
        return path;
    }

    public List<FileInfo> getListInfo() {
        return listInfo;
    }
}
