package org.vinogradov.myserver.serverLogic.storageService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {
    private static ConcurrentMap<String, Path> listUserRepositories = new ConcurrentHashMap<>();

    public static ConcurrentMap<String, Path> getListUserRepositories() {
        return listUserRepositories;
    }

    public Path createUserRepository(String nameClient) {

        Path path = Paths.get("./server/Data_Storage/" + nameClient);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        }
        listUserRepositories.put(nameClient, path);
        System.out.println(listUserRepositories);
        return path;
    }
}
