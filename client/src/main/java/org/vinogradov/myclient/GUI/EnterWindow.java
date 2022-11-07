package org.vinogradov.myclient.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.myclient.controllers.EnterWindowController;

import java.io.IOException;

public class EnterWindow {

    EnterWindowController enterWindowController;

    Stage stage;

    public EnterWindow(ClientController clientController, ClientGUI clientGUI) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinogradov/fxml/enterWin.fxml"));
        Parent newF = null;
        try {
            newF = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enterWindowController = loader.getController();
        enterWindowController.setClientController(clientController);
        stage = new Stage();
        enterWindowController.setStage(stage);
        stage.initOwner(clientGUI.getStage());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Создание файла");
        stage.setScene(new Scene(newF));
        stage.setResizable(false);
        stage.show();
    }

    public EnterWindowController getEnterWindowController() {
        return enterWindowController;
    }
}
