package org.vinogradov.myserver.serverLogic.consoleService;

import org.vinogradov.common.commonClasses.User;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConsoleLogicImpl extends DisplayingInformation implements ConsoleLogic {
    private final String badDirectory = "Директория не найдена";
    private final String badCommand = "Команда не найдена";
    private final Path rootPath = Paths.get("server/Data_Storage");
    private final String badCreate = "Пользователь не был создан";
    private final String badDelete = "Пользователь не был удален";

    private Path currentPath;
    private List<Path> currentListPath;

    private final DataBase dataBase;
    private final Storage storage;
    private final NettyServer nettyServer;
    private ConsoleGUI consoleGUI;

    public ConsoleLogicImpl(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        currentPath = rootPath;
        currentListPath = createListPaths(rootPath);
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

    public void exitConsole() {
        nettyServer.closeServer();
        consoleGUI.exit();
    }

    @Override
    public void voidCommand() {
        consoleGUI.setLog(badCommand);
    }

    @Override
    public void movePath(Path path) {
        if (path == null || !Files.exists(path) || !Files.isDirectory(path)) {
            consoleGUI.setLog(badDirectory);
            return;
        }
        if (!filterPath(path)) {
            showCurrentPath();
            return;
        }
        currentPath = path;
        currentListPath = createListPaths(path);
        showCurrentPath();
    }

    @Override
    public void appendMovePath(Path pathNameDirectory) {
        if (pathNameDirectory == null) {
            consoleGUI.setLog(badDirectory);
            return;
        }
        Path newPath = currentPath.resolve(pathNameDirectory);
        if (!Files.exists(newPath) || !Files.isDirectory(newPath)) {
            consoleGUI.setLog(badDirectory);
            return;
        }
        if (!filterPath(newPath)) {
            showCurrentPath();
            return;
        }
        currentPath = newPath;
        currentListPath = createListPaths(newPath);
        showCurrentPath();
    }

    @Override
    public void moveRoot() {
        movePath(rootPath);
    }

    @Override
    public void moveBack() {
        Path newPath = currentPath.getParent();
        if (!filterPath(newPath)) {
            showCurrentPath();
            return;
        }
        currentPath = newPath;
        currentListPath = createListPaths(currentPath);
        showCurrentPath();
    }

    @Override
    public void createNewUserInDB(String name, String password) {
        if (name != null && password != null) {
            User user = new User(name, password);
            if (!dataBase.createUser(user)) {
                consoleGUI.setLog(badCreate);
                return;
            }
            String createUser = String.format("Пользователь %s успешно создан", name);
            consoleGUI.setLog(createUser);
            return;
        }
        consoleGUI.setLog(badCreate);
    }

    @Override
    public void deleteUserInDB(String name) {
        if (name != null) {
            if (!dataBase.deleteUser(name)) {
                consoleGUI.setLog(badDelete);
                return;
            }
            String deleteUser = String.format("Пользователь %s успешно удален", name);
            consoleGUI.setLog(deleteUser);
            return;
        }
        consoleGUI.setLog(badDelete);
    }

    @Override
    public void closeNetty() {
        nettyServer.closeServer();
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

    private boolean filterPath(Path path) {
        return path.toString().contains(rootPath.toString());
    }
}

