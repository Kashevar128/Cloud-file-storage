package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.vinogradov.mydto.commonClasses.User;

public interface DataBase {

    boolean addUser(User user);

    boolean findUser(String name);

    boolean auth(User user);

    void deleteUser(String name);

    void closeDataBase();

}
