package org.vinogradov.myclient.GUI;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.myclient.clientService.NettyClient;
import org.vinogradov.myclient.controllers.RegAuthController;

import java.io.IOException;

public class RegAuthGui {

    Stage stage;

    RegAuthController regAuthController;

    public RegAuthGui() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinogradov/fxml/reg_auth.fxml"));
        Parent auth = loader.load();
        regAuthController = loader.getController();
        regAuthController.regUser();
        stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(auth));
        stage.setResizable(false);
        stage.show();

        NettyClient nettyClient = new NettyClient();
        ClientLogic clientLogic = new ClientLogic();
        regAuthController.setClientLogic(clientLogic);
        nettyClient.setClientLogic(clientLogic);
        clientLogic.setRegAuthGui(this);
        clientLogic.setNettyClient(nettyClient);

        stage.setOnCloseRequest(windowEvent -> {
            clientLogic.closeClient();
        });
    }

    public Stage getStage() {
        return stage;
    }
}
