package org.vinogradov.myserver.serverLogic.storageService;

import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.MyCallBack;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Storage {
    private final DataBase dataBase;
    private final ConcurrentMap<String, CloudUser> listUserRepositories;

    public Storage(DataBase dataBase) {
        this.dataBase = dataBase;
        this.listUserRepositories = new ConcurrentHashMap<>();
    }

    public Path createUserRepository(String nameClient) {
        Path path = Paths.get("./server/Data_Storage/" + "$$$" + nameClient + "$$$");
        CloudUser cloudUser;

        MyCallBack<Long, CloudUser> runUserCloud = (size) -> new CloudUser(nameClient,
                dataBase.getSizeStorageDB(nameClient),size);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                cloudUser = runUserCloud.call(0L);
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        } else {
            cloudUser = runUserCloud.call(HelperMethods.sumSizeFiles(path));
        }
        listUserRepositories.put(nameClient, cloudUser);
        System.out.println(listUserRepositories);
        return path;
    }

    public CloudUser getCloudUser(String name) {
        return listUserRepositories.get(name);
    }

    public ConcurrentMap<String, CloudUser> getListUserRepositories() {
        return listUserRepositories;
    }

}
