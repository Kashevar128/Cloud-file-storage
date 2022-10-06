package org.vinogradov.myclient.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.vinogradov.myclient.clientLogic.NettyClient;
import org.vinogradov.myclient.controllers.RegAuthController;

import java.io.IOException;

public class RegAuthGui {

    Stage stage;

    NettyClient nettyClient;

    public RegAuthGui() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/vinogradov/fxml/reg_auth.fxml"));
        Parent auth = loader.load();
        RegAuthController regAuthController = loader.getController();
        stage = new Stage();
        stage.setTitle("Авторизация");
        stage.setScene(new Scene(auth));
        stage.setResizable(false);
        stage.show();

        nettyClient = new NettyClient();
        regAuthController.setNettyClient(nettyClient);

    }
}
