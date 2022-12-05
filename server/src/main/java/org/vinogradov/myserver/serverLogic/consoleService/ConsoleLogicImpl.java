package org.vinogradov.myserver.serverLogic.consoleService;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConsoleLogicImpl extends DisplayingInformation implements ConsoleLogic {
    private Path currentPath;
    private List<Path> currentListPath;

    private final DataBase dataBase;
    private final Storage storage;
    private final NettyServer nettyServer;
    private ConsoleGUI consoleGUI;

    public ConsoleLogicImpl(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        this.dataBase = dataBase;
        this.storage = storage;
        this.nettyServer = nettyServer;
    }

    @Override
    public void showCurrentPath() {
        String showDirectoryFilesMsg = showDirectoryFiles(currentPath, currentListPath);
        showInGUI(showDirectoryFilesMsg);
    }

    @Override
    public void clearConsole() {
        consoleGUI.clearLog();
    }

    @Override
    public void exitServer() {
        nettyServer.closeServer();
        consoleGUI.closeConsole();
    }

    @Override
    public void voidCommand() {
        consoleGUI.setLog("Команда не найдена");
    }

    @Override
    public void movePath(Path path) {
        if(path == null || !Files.exists(path)) {
            consoleGUI.setLog("Файл не найден");
            return;
        }
        currentListPath = createListPaths(path);
        currentPath = path;
        showCurrentPath();
    }


    public void setConsoleGUI(ConsoleGUI consoleGUI) {
        this.consoleGUI = consoleGUI;
    }

    private void showInGUI(String msg) {
        if (msg == null) {
            consoleGUI.setLog("null");
            return;
        }
        consoleGUI.setLog(msg);
    }

    private List<Path> createListPaths(Path path) {
        List<Path> listFileName;
        try {
            listFileName = Files.list(path)
                    .map(Path::getFileName).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return listFileName;
    }
}

