package org.vinogradov.myserver.serverLogic.consoleService.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vinogradov.myserver.serverLogic.consoleService.ConsoleLogic;
import org.vinogradov.myserver.serverLogic.consoleService.ExecuteTheCommand;
import org.vinogradov.myserver.serverLogic.consoleService.controllers.ServerConsoleController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class ServerGUI {

    private ServerConsoleController serverConsoleController;
    private Stage stage;
    private ConsoleLogic consoleLogic;
    private ExecuteTheCommand executeTheCommand;

    public ServerGUI(ExecuteTheCommand executeTheCommand, ConsoleLogic consoleLogic) {
        try {
            this.consoleLogic = consoleLogic;
            this.executeTheCommand = executeTheCommand;
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinogradov/fxml/serverConsole.fxml"));
            Parent root = loader.load();
            serverConsoleController = loader.getController();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Java Server Console");
            stage.setOnCloseRequest(windowEvent -> {
               consoleLogic.closeNetty();
            });
            stage.setResizable(false);
            serverConsoleController.setServerGUI(this);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ServerConsoleController getServerConsoleController() {
        return serverConsoleController;
    }

    public Stage getStage() {
        return stage;
    }

    public ConsoleLogic getConsoleLogic() {
        return consoleLogic;
    }

    public ExecuteTheCommand getExecuteTheCommand() {
        return executeTheCommand;
    }
}