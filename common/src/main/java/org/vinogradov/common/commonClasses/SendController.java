package org.vinogradov.common.commonClasses;

import java.util.Map;

public interface SendController {

    public long addNewSrcPath(String srcPath);

    public Map<Long, String> getSrcPathsMap();

    public void clearSrcPathsMap();
}
