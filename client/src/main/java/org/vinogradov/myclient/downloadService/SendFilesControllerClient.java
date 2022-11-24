package org.vinogradov.myclient.downloadService;

import java.util.HashMap;
import java.util.Map;

public class SendFilesControllerClient {
    private final GenerateIdFile generateIdFile;
    private Map<Long, String> srcPathsMap;



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
}
