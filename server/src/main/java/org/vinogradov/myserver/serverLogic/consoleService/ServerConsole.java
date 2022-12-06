package org.vinogradov.myserver.serverLogic.consoleService;

import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

import javax.swing.*;

public class ServerConsole {

    private final DataBase dataBase;
    private final Storage storage;


    public ServerConsole(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        this.dataBase = dataBase;
        this.storage = storage;
        SwingUtilities.invokeLater(() -> {
            ConsoleLogicImpl consoleLogicImpl = new ConsoleLogicImpl(dataBase, storage, nettyServer);
            ExecuteTheCommand executeTheCommand = new ExecuteTheCommand(consoleLogicImpl);
            ConsoleGUI consoleGUI = new ConsoleGUI(executeTheCommand, consoleLogicImpl);
            consoleLogicImpl.setConsoleGUI(consoleGUI);
        });
    }

}