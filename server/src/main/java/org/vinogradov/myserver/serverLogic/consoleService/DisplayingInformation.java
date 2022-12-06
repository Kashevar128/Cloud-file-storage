package org.vinogradov.myserver.serverLogic.consoleService;

import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class DisplayingInformation {

    public String showDirectoryFiles(Path currentPath, List<Path> fileNamesList) {
        String pathStr = "Текущая директория: " + currentPath + "\n";
        if(fileNamesList == null) return null;
        String arrayStr = "Файлы директории: " + Arrays.toString(fileNamesList.toArray());
        return pathStr.concat(arrayStr);
    }

    public String showAllUsersDB(List<List<String>> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> ls : users) {
            stringBuilder.append(Arrays.toString(ls.toArray())).append("\n");
        }
        return stringBuilder.toString();
    }

    public String showUserDB(List<String> infoUser) {
        return Arrays.toString(infoUser.toArray()) + "\n";
    }

    public String showUsersOnline(List<String> userOnline) {
        return Arrays.toString(userOnline.toArray());
    }

    public String showHelp() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("newUser [username,password] - создать нового пользователя в базе данных").append("\n")
                .append("delUser [username] - удалить пользователя в базе данных").append("\n")
                .append("back - перейти на одну директорию назад").append("\n")
                .append("move - перейти на одну директорию вперед").append("\n")
                .append("ban [username] - забанить пользователя").append("\n")
                .append("unBan [nameUser] - разбанить пользователя").append("\n")
                .append("newFolder [folderName] - создать новую папку").append("\n")
                .append("delFile [fileName] - удалить папку или файл " +
                        "(нужно находиться в родительской директории файла или папки)").append("\n")
                .append("clear - очистить консоль").append("\n")
                .append("exit - выйти из консоли и сервера").append("\n")
                .append("curPath - показать текущий путь").append("\n")
                .append("root - переместиться в корневой каталог сервера").append("\n")
                .append("entry [entryPath] - перейти по указанному пути").append("\n")
                .append("allUsers - показать всех пользователей из базы данных").append("\n")
                .append("getUser [nameUser] - показать конкретного пользователя из базы данных").append("\n")
                .append("setSizeStorage [username,sizeStorage],").append("\n")
                .append("значения для sizeStorage: Gb2, Gb5, Gb10, Gb20").append("\n")
                .append("listOnline - показать список пользователей online").append("\n")
                .append("help - вызов помощи");
        return stringBuilder.toString();
    }
}