package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.vinogradov.common.commonClasses.User;

import java.util.List;

public interface DataBase {

    boolean createUser(User user);

    boolean findUser(String name);

    boolean auth(User user);

    boolean deleteUser(String name);

    void closeDataBase();

    List<List<String>> showAllUser();

    List<String> showUser(String name);

    void setAccess(String name, int param);

    boolean getAccess(String name);

}
