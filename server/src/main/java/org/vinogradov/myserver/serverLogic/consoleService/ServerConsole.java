package org.vinogradov.myserver.serverLogic.consoleService;

import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

public class ServerConsole {

    public ServerConsole() {
        DataBase dataBase;
        try {
            dataBase = new DataBaseImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Storage storage = new Storage();
        ConsoleLogic consoleLogic = new ConsoleLogic(dataBase, storage);
        ExecuteTheCommand executeTheCommand = new ExecuteTheCommand(consoleLogic);
        EnteringCommands enteringCommands = new EnteringCommands(executeTheCommand);

        new Thread(enteringCommands).start();
    }

}