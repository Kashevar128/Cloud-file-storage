package org.vinogradov.myserver.serverLogic.consoleService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.Constants;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.User;
import org.vinogradov.myserver.serverLogic.consoleService.controllers.ServerConsoleController;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;
import org.vinogradov.myserver.serverLogic.serverService.UserContextRepository;
import org.vinogradov.myserver.serverLogic.storageService.CloudUser;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class ConsoleLogicImpl extends DisplayingInformation implements ConsoleLogic {
    private final String badDirectory = "Директория не найдена";
    private final String badCommand = "Команда не найдена";
    private final Path rootPath = Paths.get("server/Data_Storage");
    private final String badCreateUser = "Пользователь не был создан";
    private final String badDeleteUser = "Пользователь не был удален";
    private final String badCreateFolder = "Ошибка при создании папки";
    private final String badDelFile = "Такого файла нет";
    private final String errorDel = "Ошибка при удалении файла";
    private final String badRequestUser = "Пользователь не найден";
    private final String badSizeUser = "Не найден указанный размер";


    private Path currentPath;
    private List<Path> currentListPath;
    private SizeConstants sizeConstants;

    private final DataBase dataBase;
    private final Storage storage;
    private final NettyServer nettyServer;
    private ServerConsoleController serverConsoleController;

    public ConsoleLogicImpl(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        currentPath = rootPath;
        currentListPath = createListPaths(rootPath);
        this.sizeConstants = new SizeConstants();
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
        serverConsoleController.clearLog();
    }

    public void exitConsole() {
        nettyServer.closeServer();
        serverConsoleController.exit();
    }

    @Override
    public void voidCommand() {
        serverConsoleController.setLog(badCommand);
    }

    @Override
    public void movePath(Path path) {
        if (path == null || !Files.exists(path) || !Files.isDirectory(path)) {
            serverConsoleController.setLog(badDirectory);
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
            serverConsoleController.setLog(badDirectory);
            return;
        }
        Path newPath = currentPath.resolve(pathNameDirectory);
        if (!Files.exists(newPath) || !Files.isDirectory(newPath)) {
            serverConsoleController.setLog(badDirectory);
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
        if (name != null && password != null && !name.isEmpty() && !password.isEmpty() ) {
            User user = new User(name, password);
            if (!dataBase.createUser(user)) {
                serverConsoleController.setLog(badCreateUser);
                return;
            }
            String createUser = String.format("Пользователь %s успешно создан", name);
            serverConsoleController.setLog(createUser);
            return;
        }
        serverConsoleController.setLog(badCreateUser);
    }

    @Override
    public void deleteUserInDB(String name) {
        if (name != null && !name.isEmpty()) {
            if (!dataBase.deleteUser(name)) {
                serverConsoleController.setLog(badDeleteUser);
                return;
            }
            String deleteUser = String.format("Пользователь %s успешно удален", name);
            serverConsoleController.setLog(deleteUser);
            return;
        }
        serverConsoleController.setLog(badDeleteUser);
    }

    @Override
    public void closeNetty() {
        nettyServer.closeServer();
    }

    @Override
    public void createNewFolder(String nameFolder) {
        if (nameFolder == null || nameFolder.isEmpty()) {
            serverConsoleController.setLog(badCreateFolder);
            return;
        }
        currentPath = currentPath.resolve(Paths.get(nameFolder));
        HelperMethods.createNewUserDirectory(currentPath);
        currentListPath = createListPaths(currentPath);
        String createFolder = String.format("Папка %s успешно создана", nameFolder);
        serverConsoleController.setLog(createFolder);
    }

    @Override
    public void deleteFile(String nameFolder) {
        if (nameFolder == null || nameFolder.isEmpty()) {
            serverConsoleController.setLog(badDelFile);
            return;
        }
        Path resolve = currentPath.resolve(Paths.get(nameFolder));
        if (!Files.exists(resolve)) {
            serverConsoleController.setLog(badDelFile);
            return;
        }
        if (!HelperMethods.deleteUserFile(resolve)) {
            serverConsoleController.setLog(errorDel);
        }
        String delComplete = String.format("Файл %s успешно удален", nameFolder);
        serverConsoleController.setLog(delComplete);
        currentListPath = createListPaths(currentPath);
        showCurrentPath();
    }

    @Override
    public void getListDB() {
        List<List<String>> lists = dataBase.showAllUser();
        String showUsersDB = showAllUsersDB(lists);
        serverConsoleController.setLog(showUsersDB);
    }

    @Override
    public void getUserDB(String name) {
        if(name == null || name.isEmpty()) {
            serverConsoleController.setLog(badRequestUser);
            return;
        }
        List<String> list = dataBase.showUser(name);
        if (list.get(0) == null) {
            serverConsoleController.setLog(badRequestUser);
            return;
        }
        String showUserDB = showUserDB(list);
        serverConsoleController.setLog(showUserDB);
    }

    @Override
    public void banUser(String name) {
        if (name == null || name.isEmpty() || !dataBase.findUser(name)) {
            serverConsoleController.setLog(badRequestUser);
            return;
        }
        dataBase.setAccess(name, Constants.ACCESS_FALSE);
        UserContextRepository userContextRepository = nettyServer.getUserContextRepository();
        ChannelHandlerContext context = userContextRepository.getContext(name);
        if (context != null) {
            context.close();
            userContextRepository.deleteUserContext(name);
        }
        String endConnection = String.format("Пользователь %s ушел в бан", name);
        serverConsoleController.setLog(endConnection);
    }

    @Override
    public void unBanUser(String name) {
        if (name == null || name.isEmpty() || !dataBase.findUser(name)) {
            serverConsoleController.setLog(badRequestUser);
            return;
        }
        dataBase.setAccess(name, Constants.ACCESS_TRUE);
        String unBan = String.format("Пользователь %s разбанен", name);
        serverConsoleController.setLog(unBan);
    }

    @Override
    public void setSizeStorage(String name, String sizeStr) {
        if (name == null || name.isEmpty()) {
            serverConsoleController.setLog(badRequestUser);
            return;
        }
        if (sizeStr == null || sizeStr.isEmpty()) {
            serverConsoleController.setLog(badSizeUser);
            return;
        }
        long constant = sizeConstants.getConstant(sizeStr);
        if (constant == 0L) {
            serverConsoleController.setLog(badSizeUser);
            return;
        }
        dataBase.setSizeStorageDB(name, constant);
        ConcurrentMap<String, CloudUser> listUserRepositories = storage.getListUserRepositories();
        CloudUser cloudUser = listUserRepositories.get(name);
        String reSizeComplete = String.format("Размер для %s успешно изменен", name);
        serverConsoleController.setLog(reSizeComplete);
        if (cloudUser == null) return;
        cloudUser.setMaxSize(constant);

    }

    @Override
    public void getUsersOnline() {
        UserContextRepository userContextRepository = nettyServer.getUserContextRepository();
        String usersOnline = showUsersOnline(userContextRepository.getUserList());
        serverConsoleController.setLog(usersOnline);
    }

    @Override
    public void getHelp() {
        serverConsoleController.setLog(showHelp());
    }

    public void setServerConsoleController(ServerConsoleController serverConsoleController) {
        this.serverConsoleController = serverConsoleController;
    }


        private void showInGUI(String msg) {
        if (msg == null || msg.isEmpty()) {
            serverConsoleController.setLog("null");
            return;
        }
        serverConsoleController.setLog(msg);
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

