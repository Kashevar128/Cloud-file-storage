package org.vinogradov.myserver.serverLogic.sendFileServerService;

import org.vinogradov.common.commonClasses.GenerateIdFile;
import org.vinogradov.common.commonClasses.SendController;

import java.util.HashMap;
import java.util.Map;

public class SendFileServerController implements SendController {

    private final GenerateIdFile generateIdFile;

    private Map<Long, String> srcPathsMap;

    public SendFileServerController() {
        this.generateIdFile = new GenerateIdFile();
        this.srcPathsMap = new HashMap<>();
    }

    @Override
    public long addNewSrcPath(String srcPath) {
        long idFile = generateIdFile.getIdFile();
        srcPathsMap.put(idFile, srcPath);
        return idFile;
    }

    @Override
    public Map<Long, String> getSrcPathsMap() {
        return srcPathsMap;
    }

    @Override
    public void clearSrcPathsMap() {
        srcPathsMap = new HashMap<>();
    }
}
