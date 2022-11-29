package org.vinogradov.myserver.serverLogic.storageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {
    private static ConcurrentMap<String, CloudUser> listUserRepositories = new ConcurrentHashMap<>();

    public Path createUserRepository(String nameClient) {

        Path path = Paths.get("./server/Data_Storage/" + "$$$" + nameClient + "$$$");
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        }
        CloudUser cloudUser = new CloudUser(path.toString());
        listUserRepositories.put(nameClient, cloudUser);
        System.out.println(listUserRepositories);
        return path;
    }

    public CloudUser getCloudUser(String name) {
        return listUserRepositories.get(name);
    }

    public static ConcurrentMap<String, CloudUser> getListUserRepositories() {
        return listUserRepositories;
    }
}
