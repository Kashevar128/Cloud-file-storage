package org.vinogradov.myserver.serverLogic.consoleService;

import java.nio.file.Paths;

public class ExecuteTheCommand {
    private String command;
    private String info;
    private String info_1;
    private String info_2;
    private final ConsoleLogic consoleLogic;
    private final ListCommand listCommand;

    public ExecuteTheCommand(ConsoleLogicImpl consoleLogicImpl) {
        this.command = null;
        this.info = null;
        this.info_1 = null;
        this.info_2 = null;
        this.consoleLogic = consoleLogicImpl;
        this.listCommand = new ListCommand();
    }

    public void execute(String msg) {
        boolean handlerCommand = handlerCommand(msg);
        if (!handlerCommand) return;
        ListCommand.CommandEnum commandEnum = listCommand.getCommand(command);

        switch (commandEnum) {

            case CLEAR -> consoleLogic.clearConsole();

            case EXIT -> consoleLogic.exitConsole();

            case CURRENT_PATH -> consoleLogic.showCurrentPath();

            case MOVE -> consoleLogic.appendMovePath(Paths.get(info));

            case ROOT -> consoleLogic.moveRoot();

            case ENTRY -> consoleLogic.movePath(Paths.get(info));

            case BACK -> consoleLogic.moveBack();

            case CREATE_NEW_USER -> consoleLogic.createNewUserInDB(info_1, info_2);

            case DELETE_USER -> consoleLogic.deleteUserInDB(info);

            case CREATE_NEW_PACKAGE -> consoleLogic.createNewFolder(info);

            case DELETE_PACKAGE -> consoleLogic.deleteFile(info);

            case All_USERS_DB -> consoleLogic.showListDB();

            case USER_DB -> consoleLogic.showUserDB(info);

            case BAN_USER -> consoleLogic.banUser(info);

            case UNBAN_USER -> consoleLogic.unBanUser(info);

            case SET_SIZE_STORAGE -> consoleLogic.setSizeStorage(info_1, info_2);

            case USERS_ONLINE -> consoleLogic.showUsersOnline();
        }
    }

    private boolean handlerCommand(String msg) {
        String[] strings = msg.split(" ", 2);
        command = strings[0].trim();
        if (strings.length > 1)  {
            info = strings[1].trim();
            if (info.contains(",")) {
                String[] split = info.split(",", 2);
                info_1 = split[0];
                info_2 = split[1];
            }
        }
        if (!listCommand.isACommand(command)) {
            consoleLogic.voidCommand();
            return false;
        }
        return true;
    }
}
