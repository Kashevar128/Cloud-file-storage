package org.vinogradov.myclient.downloadService;

import java.util.HashMap;
import java.util.Map;

public class DownloadController {
    GenerateIdFile generateIdFile;
    Map<String, Map<Long, String>> filesMap;

    public DownloadController() {
        this.generateIdFile = new GenerateIdFile();
        this.filesMap = new HashMap<>();
    }

    private Long getUniqIdFile() {
        return generateIdFile.getIdFile();
    }

    public long addFilePath(String fileName, String srcPath) {
        long id = getUniqIdFile();
        filesMap.get(fileName).put(id, srcPath);
        return id;
    }

    public String getPathFile(String fileName, long id) {
        return filesMap.get(fileName).get(id);
    }

    public Map<Long, String> getPathsMapFile(String fileName) {
        return filesMap.get(fileName);
    }

    public void removeFileFromQueue(String fileName) {
        filesMap.remove(fileName);
    }

    public void addNewFileInQueue(String nameFile) {
        filesMap.put(nameFile, new HashMap<>());
    }
}
