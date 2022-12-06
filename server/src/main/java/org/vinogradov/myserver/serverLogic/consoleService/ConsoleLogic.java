package org.vinogradov.myserver.serverLogic.consoleService;

import java.nio.file.Path;

public interface ConsoleLogic {

    void showCurrentPath();

    void clearConsole();

    void exitConsole();

    void voidCommand();

    void movePath(Path path);

    void appendMovePath(Path nameDirectory);

    void moveRoot();

    void moveBack();

    void createNewUserInDB(String name, String password);

    void deleteUserInDB(String name);

    void closeNetty();

    void createNewFolder(String nameFolder);

    void deleteFile(String nameFolder);

    void showListDB();

    void showUserDB(String name);

    void banUser(String name);

    void unBanUser(String name);

    void setSizeStorage(String name, String size);

}
