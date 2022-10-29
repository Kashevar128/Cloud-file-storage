package org.vinogradov.myserver.serverLogic.dataBaseService;

public interface DataBase {

    boolean createUser(String name, String password);

    boolean findUser(String name);

    boolean auth(String name, String password);

    void deleteUser(String name);

    void startDataBase();

    void stopDataBase();

}
