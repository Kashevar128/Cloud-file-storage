package org.vinogradov.myserver.serverLogic.consoleService;

public class ExecuteTheCommand {

    private final ConsoleLogic consoleLogic;
    private final ListCommand listCommand;

    public ExecuteTheCommand(ConsoleLogic consoleLogic) {
        this.consoleLogic = consoleLogic;
        this.listCommand = new ListCommand();
    }

    public void execute(String command) {
        ListCommand.CommandEnum commandEnum = listCommand.getCommand(command);

        switch (commandEnum) {

            case SHOW_STORAGE -> consoleLogic.openServerStorage();

        }
    }

}
