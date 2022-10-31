package org.vinogradov.myserver.serverLogic.ConnectionsService;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TemporaryDataBase {

    private final ConcurrentMap<String, String> usersTemporaryDataBase = new ConcurrentHashMap<>();

    public void addUserData(String name, String password) {
        if (!usersTemporaryDataBase.containsKey(name)) {
            usersTemporaryDataBase.put(name, password);
        }
    }

    public String getUserPassword(String name) {
        if (usersTemporaryDataBase.containsKey(name)) {
            return usersTemporaryDataBase.get(name);
        }
        return null;
    }

    public void deleteUserData(String name) {
        if (usersTemporaryDataBase.containsKey(name)) {
            usersTemporaryDataBase.remove(name);
        }
    }
}
