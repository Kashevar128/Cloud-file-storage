package org.vinogradov.myserver.serverLogic.consoleService;

import org.vinogradov.common.commonClasses.Constants;

import java.util.HashMap;
import java.util.Map;

public class ListCommand {

    private final Map<String, CommandEnum> commandEnumMap;

    public ListCommand() {
        this.commandEnumMap = new HashMap<>();
        commandEnumMap.put("newUser", CommandEnum.CREATE_NEW_USER);
        commandEnumMap.put("delUser", CommandEnum.DELETE_USER);
        commandEnumMap.put("back", CommandEnum.BACK);
        commandEnumMap.put("move", CommandEnum.MOVE);
        commandEnumMap.put("ban", CommandEnum.BAN_USER);
        commandEnumMap.put("newFolder", CommandEnum.CREATE_NEW_PACKAGE);
        commandEnumMap.put("delFile", CommandEnum.DELETE_PACKAGE);
        commandEnumMap.put("cancel", CommandEnum.CANCEL);
        commandEnumMap.put("clear", CommandEnum.CLEAR);
        commandEnumMap.put("exit", CommandEnum.EXIT);
        commandEnumMap.put("currentPath", CommandEnum.CURRENT_PATH);
        commandEnumMap.put("root", CommandEnum.ROOT);
        commandEnumMap.put("entry", CommandEnum.ENTRY);
        commandEnumMap.put("allUsers", CommandEnum.All_USERS_DB);
        commandEnumMap.put("getUser", CommandEnum.USER_DB);
        commandEnumMap.put("unBan", CommandEnum.UNBAN_USER);
        commandEnumMap.put("setSizeStorage", CommandEnum.SET_SIZE_STORAGE);
    }

    public CommandEnum getCommand(String command) {
        return commandEnumMap.get(command);
    }

    public boolean isACommand(String command) {
        return commandEnumMap.containsKey(command);
    }

    public enum CommandEnum {
        CREATE_NEW_USER,
        DELETE_USER,
        BACK,
        MOVE,
        BAN_USER,
        CREATE_NEW_PACKAGE,
        DELETE_PACKAGE,
        CANCEL,
        CLEAR,
        EXIT,
        CURRENT_PATH,
        ROOT,
        ENTRY,
        All_USERS_DB,
        USER_DB,
        UNBAN_USER,
        SET_SIZE_STORAGE
    }


}
