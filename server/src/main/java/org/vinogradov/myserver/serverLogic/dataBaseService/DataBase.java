package org.vinogradov.myserver.serverLogic.dataBaseService;

public interface DataBase {

    void createUser(String name, String password);

    boolean findUser(String name);

    void deleteUser(String name);

    void start();

    void stop();

}
