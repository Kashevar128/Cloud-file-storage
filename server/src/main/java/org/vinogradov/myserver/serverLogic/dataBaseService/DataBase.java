package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.vinogradov.common.commonClasses.User;

public interface DataBase {

    boolean createUser(User user);

    boolean findUser(String name);

    boolean auth(User user);

    void deleteUser(String name);

    void startDataBase();

    void closeDataBase();

}
