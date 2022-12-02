package org.vinogradov.myserver.serverLogic.consoleService;

import java.util.HashMap;
import java.util.Map;

public class ListCommand {

    private final Map<String, CommandEnum> commandEnumMap;

    public ListCommand() {
        this.commandEnumMap = new HashMap<>();
        commandEnumMap.put("new user", CommandEnum.CREATE_NEW_USER);
        commandEnumMap.put("del user", CommandEnum.DELETE_USER);
        commandEnumMap.put("entry", CommandEnum.ENTRY_PATH);
        commandEnumMap.put("back", CommandEnum.BACK);
        commandEnumMap.put("move", CommandEnum.MOVE);
        commandEnumMap.put("ban", CommandEnum.BAN_USER);
        commandEnumMap.put("create file", CommandEnum.CREATE_NEW_PACKAGE);
        commandEnumMap.put("del package", CommandEnum.DELETE_PACKAGE);
        commandEnumMap.put("storage", CommandEnum.SHOW_STORAGE);
        commandEnumMap.put("cancel", CommandEnum.CANCEL);
    }

    public CommandEnum getCommand(String command) {
        return commandEnumMap.get(command);
    }

    public enum CommandEnum {
        CREATE_NEW_USER,
        DELETE_USER,
        ENTRY_PATH,
        BACK,
        MOVE,
        BAN_USER,
        CREATE_NEW_PACKAGE,
        DELETE_PACKAGE,
        SHOW_STORAGE,
        CANCEL
    }


}
