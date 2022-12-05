package org.vinogradov.myserver.serverLogic.consoleService;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecuteTheCommand {

    private final Path rootPath = Paths.get("server/Data_Storage");
    private final ConsoleLogic consoleLogic;
    private final ListCommand listCommand;

    public ExecuteTheCommand(ConsoleLogicImpl consoleLogicImpl) {
        this.consoleLogic = consoleLogicImpl;
        this.listCommand = new ListCommand();
    }

    public void execute(String msg) {
        String info = null;
        String[] strings = handlerCommand(msg);
        String command = strings[0];
        if (strings.length > 1) info = strings[1];
        if (!listCommand.isACommand(command)) {
            consoleLogic.voidCommand();
            return;
        }
        ListCommand.CommandEnum commandEnum = listCommand.getCommand(command);

        switch (commandEnum) {
            case SHOW_STORAGE -> consoleLogic.movePath(rootPath);

            case CLEAR -> consoleLogic.clearConsole();

            case EXIT -> consoleLogic.exitServer();

            case CURRENT_PATH -> consoleLogic.showCurrentPath();

            case MOVE -> consoleLogic.movePath(Paths.get(info));
        }
    }

    private String[] handlerCommand(String command) {
        return command.split(" ", 2);
    }

}
