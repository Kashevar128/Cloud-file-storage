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

    public String showUsersDB(List<List<String>> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (List<String> ls : users) {
            stringBuilder.append(Arrays.toString(ls.toArray())).append("\n");
        }
        return stringBuilder.toString();
    }

    public void showUsersOnline(Map<String, ChannelHandlerContext> usersOnlineMap) {

    }
}