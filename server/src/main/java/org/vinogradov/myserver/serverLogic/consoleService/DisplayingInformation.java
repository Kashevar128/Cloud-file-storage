package org.vinogradov.myserver.serverLogic.consoleService;

import io.netty.channel.ChannelHandlerContext;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DisplayingInformation {

    public void showNameFiles(Path currentPath, List<Path> fileNamesList) {
        String pathStr = currentPath + "/";
        System.out.println(pathStr);
        System.out.println(Arrays.toString(fileNamesList.toArray()));
    }

    public void showUsersDB(Map<String, String> usersMap) {

    }

    public void showUsersOnline(Map<String, ChannelHandlerContext> usersOnlineMap) {

    }
}
