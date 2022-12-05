package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.vinogradov.common.commonClasses.User;

public interface DataBase {

    boolean createUser(User user);

    boolean findUser(String name);

    boolean auth(User user);

    boolean deleteUser(String name);

    void closeDataBase();

}
