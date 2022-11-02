package org.vinogradov.myserver.serverLogic.connectionsService;

import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class FileChannelRepository {

    private ConcurrentMap<String, FileOutputStream> channelConcurrentMap = new ConcurrentHashMap<>();

    public void addFileChannel (String path, FileOutputStream fileChannel) {
        if (!channelConcurrentMap.containsKey(path)) {
            channelConcurrentMap.put(path, fileChannel);
        }
    }

    public void deleteFileChannel (String path) {
        if (channelConcurrentMap.containsKey(path)) {
            channelConcurrentMap.remove(path);
        }
    }

    public FileOutputStream getFileChannel (String path) {
        return channelConcurrentMap.get(path);
    }
}
