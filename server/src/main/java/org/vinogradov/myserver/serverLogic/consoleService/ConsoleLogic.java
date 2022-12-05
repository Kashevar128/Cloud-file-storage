package org.vinogradov.myserver.serverLogic.consoleService;

import java.nio.file.Path;

public interface ConsoleLogic {

    void showCurrentPath();

    void clearConsole();

    void exitServer();

    void voidCommand();

    void movePath(Path path);

}
