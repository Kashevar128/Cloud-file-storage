package org.vinogradov.myserver.serverLogic.consoleService;

import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DisplayingInformation {

    public String showDirectoryFiles(Path currentPath, List<Path> fileNamesList) {
        String pathStr = "Текущая директория: " + currentPath + "\n";
        if(fileNamesList == null) return null;
        String arrayStr = "Файлы директории: " + Arrays.toString(fileNamesList.toArray());
        return pathStr.concat(arrayStr);
    }

    public void showUsersDB(Map<String, String> usersMap) {

    }

    public void showUsersOnline(Map<String, ChannelHandlerContext> usersOnlineMap) {

    }
}