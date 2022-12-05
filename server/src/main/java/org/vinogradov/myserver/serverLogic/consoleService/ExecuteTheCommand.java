package org.vinogradov.myserver.serverLogic.consoleService;

import java.nio.file.Paths;

public class ExecuteTheCommand {
    private String command;
    private String info;
    private String nameUser;
    private String password;
    private final ConsoleLogic consoleLogic;
    private final ListCommand listCommand;

    public ExecuteTheCommand(ConsoleLogicImpl consoleLogicImpl) {
        this.command = null;
        this.info = null;
        this.nameUser = null;
        this.password = null;
        this.consoleLogic = consoleLogicImpl;
        this.listCommand = new ListCommand();
    }

    public void execute(String msg) {
        boolean handlerCommand = handlerCommand(msg);
        if (!handlerCommand) return;
        ListCommand.CommandEnum commandEnum = listCommand.getCommand(command);

        switch (commandEnum) {

            case CLEAR -> consoleLogic.clearConsole();

            case EXIT -> consoleLogic.exitServer();

            case CURRENT_PATH -> consoleLogic.showCurrentPath();

            case MOVE -> consoleLogic.appendMovePath(Paths.get(info));

            case ROOT -> consoleLogic.moveRoot();

            case ENTRY -> consoleLogic.movePath(Paths.get(info));

            case BACK -> consoleLogic.moveBack();
        }
    }

    private boolean handlerCommand(String msg) {
        String[] strings = msg.split(" ", 3);
        command = strings[0].trim();
        if (strings.length > 1) info = strings[1].trim();
        if (info.contains(","))
        if (!listCommand.isACommand(command)) {
            consoleLogic.voidCommand();
            return false;
        }
        return true;
    }
}
