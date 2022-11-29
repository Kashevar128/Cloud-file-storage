package org.vinogradov.myserver.serverLogic.connectionService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ConverterPath {

    private final String pathRootDirectoryUser;

    private final String nameRootDirectoryUser;

    private Map<Long, String> clientPathsMap;

    private Map<Long, String> serverPathsMap;

    private String clientPathString;

    private String serverPathString;

    private String parentClientPathString;

    private String parentServerPathString;

    private Path clientPathToPath;

    private Path serverPathToPath;

    private Path parentClientPathToPath;

    private Path parentServerPathToPath;

    public ConverterPath(String pathRootDirectoryUser) {
        this.pathRootDirectoryUser = pathRootDirectoryUser;
        this.nameRootDirectoryUser = Paths.get(pathRootDirectoryUser).getFileName().toString();
    }

    public void setPathsMap(Map<Long, String> map, boolean flag) {
        if (flag) {
            clientPathsMap = map;
            serverPathsMap = new HashMap<>();
            for (Map.Entry<Long, String> entry : map.entrySet()) {
                serverPathsMap.put(entry.getKey(), restoringPath(entry.getValue()));
            }
        } else {
            serverPathsMap = map;
            clientPathsMap = new HashMap<>();
            for (Map.Entry<Long, String> entry : map.entrySet()) {
                clientPathsMap.put(entry.getKey(), editingPath(entry.getValue()));
            }
        }
    }

    public void setPath(String path, boolean flag) {
        if (flag) {
            clientPathString = path;
            clientPathToPath = Paths.get(path);
            parentClientPathToPath = clientPathToPath.getParent();
            if (parentClientPathToPath != null) parentClientPathString = parentClientPathToPath.toString();
            serverPathString = restoringPath(path);
            serverPathToPath = Paths.get(serverPathString);
            parentServerPathToPath = serverPathToPath.getParent();
            if (parentClientPathToPath != null) parentServerPathString = parentServerPathToPath.toString();
        } else {
            serverPathString = path;
            serverPathToPath = Paths.get(serverPathString);
            parentServerPathToPath = serverPathToPath.getParent();
            if (parentServerPathToPath != null) parentServerPathString = parentServerPathToPath.toString();
            clientPathString = editingPath(path);
            clientPathToPath = Paths.get(clientPathString);
            parentClientPathToPath = clientPathToPath.getParent();
            if (parentClientPathToPath != null)parentClientPathString = parentClientPathToPath.toString();
        }
    }

    private String editingPath(String path) {
        int s = path.indexOf(nameRootDirectoryUser);
        return path.substring(s);
    }

    private String restoringPath(String path) {
        String replace = pathRootDirectoryUser.replace(nameRootDirectoryUser, "");
        return replace + path;
    }

    public Map<Long, String> getClientPathsMap() {
        return clientPathsMap;
    }

    public Map<Long, String> getServerPathsMap() {
        return serverPathsMap;
    }

    public String getClientPathString() {
        return clientPathString;
    }

    public String getServerPathString() {
        return serverPathString;
    }

    public String getParentClientPathString() {
        return parentClientPathString;
    }

    public String getParentServerPathString() {
        return parentServerPathString;
    }

    public Path getClientPathToPath() {
        return clientPathToPath;
    }

    public Path getServerPathToPath() {
        return serverPathToPath;
    }

    public Path getParentClientPathToPath() {
        return parentClientPathToPath;
    }

    public Path getParentServerPathToPath() {
        return parentServerPathToPath;
    }
}
