package org.vinogradov.myclient.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.myclient.controllers.ClientController;

import java.io.IOException;

/**
 * JavaFX App
 */
public class ClientGUI {

    private ClientController clientController;

    public ClientGUI(ClientLogic clientLogic) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinogradov/fxml/clientWindow.fxml"));
            Parent root = loader.load();
            clientController = loader.getController();
            stage.setTitle("Java File Storage");
            stage.setScene(new Scene(root, 1000, 600));
            stage.setOnCloseRequest(windowEvent -> {
               clientLogic.exitUserClient();
            });
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ClientController getClientController() {
        return clientController;
    }
}