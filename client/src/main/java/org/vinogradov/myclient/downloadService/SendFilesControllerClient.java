package org.vinogradov.myclient.downloadService;

import org.vinogradov.common.commonClasses.FilePaths;

import java.util.Map;

public class SendFilesControllerClient {
    private final GenerateIdFile generateIdFile;
    private FilePaths fileSrcPaths;

    public SendFilesControllerClient() {
        this.generateIdFile = new GenerateIdFile();
    }

    private Long getUniqIdFile() {
        return generateIdFile.getIdFile();
    }

    public void createNewSendFile(String fileOrDirectoryName) {
        fileSrcPaths = new FilePaths(fileOrDirectoryName);
    }

    public Map<Long, String> getMapSrcPaths (String fileOrDirectoryName) {
        if (fileSrcPaths.getFileOrDirectoryName().equals(fileOrDirectoryName)) {
            return fileSrcPaths.getPaths();
        }
        return null;
    }

    public Long addNewSrcPath(String srcPath) {
        long id = getUniqIdFile();
        fileSrcPaths.addStringPath(id, srcPath);
        return id;
    }
}
