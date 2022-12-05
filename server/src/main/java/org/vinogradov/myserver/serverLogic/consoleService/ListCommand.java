package org.vinogradov.myserver.serverLogic.consoleService;

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
        ENTRY
    }


}
