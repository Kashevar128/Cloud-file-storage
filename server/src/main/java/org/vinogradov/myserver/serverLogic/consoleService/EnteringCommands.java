package org.vinogradov.myserver.serverLogic.consoleService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class EnteringCommands implements Runnable {

    private final Scanner scanner;

    private final ExecuteTheCommand executeTheCommand;

    public EnteringCommands( ExecuteTheCommand executeTheCommand) {
        this.scanner = new Scanner(System.in);
        this.executeTheCommand = executeTheCommand;
        System.out.print("Enter the command: ");
    }

    @Override
    public void run() {
        String command;
        while(scanner.hasNext()) {
            command = scanner.nextLine();
            System.out.println("You entered the command: " + command);
            executeTheCommand.execute(command);
            System.out.print("Enter the command: ");
        }
    }
}
