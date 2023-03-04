package org.vinogradov.myserver.serverLogic.consoleService;

import javafx.application.Platform;
import org.vinogradov.myserver.serverLogic.consoleService.GUI.ServerGUI;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

public class ServerConsole {

    private final DataBase dataBase;
    private final Storage storage;
    private ServerGUI serverGUI;


    public ServerConsole(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        this.dataBase = dataBase;
        this.storage = storage;

        Platform.startup(() -> {
            ConsoleLogicImpl consoleLogicImpl = new ConsoleLogicImpl(dataBase, storage, nettyServer);
            ExecuteTheCommand executeTheCommand = new ExecuteTheCommand(consoleLogicImpl);
            this.serverGUI = new ServerGUI(executeTheCommand, consoleLogicImpl);
            consoleLogicImpl.setServerConsoleController(serverGUI.getServerConsoleController());
        });
    }

    public ServerGUI getServerGUI() {
        return serverGUI;
    }
}