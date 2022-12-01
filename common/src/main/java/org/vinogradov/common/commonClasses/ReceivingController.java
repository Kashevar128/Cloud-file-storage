package org.vinogradov.common.commonClasses;

import java.util.Map;

public interface ReceivingController {

    void addFileOutputStreamMap(Map<Long, String> dstPathsMap);

    void addBytesInFileOutputStream(Long id, byte[] bytes);

    void createCounterFileSize(long referenceSize);

    void addSizePartInCounter(long sizePart);

    boolean sizeFileCheck();

    void closeAllFileOutputStreams();
}
