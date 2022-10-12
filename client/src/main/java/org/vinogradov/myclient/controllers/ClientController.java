package org.vinogradov.myclient.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.vinogradov.myclient.clientLogic.NettyClient;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private NettyClient nettyClient;

    private PanelController srcPC = null, dstPC = null;

    private Path srcPath = null, dstPath = null;

    @FXML
    public VBox clientPanel, serverPanel;

    public PanelClientController clientPC;
    public PanelServerController serverPC;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clientPC = (PanelClientController) clientPanel.getProperties().get("ctrl");
        serverPC = (PanelServerController) serverPanel.getProperties().get("ctrl");
    }

    @FXML
    public void exitBtnAction(ActionEvent actionEvent) {
        Platform.exit();
        nettyClient.exitClient();
    }

    @FXML
    public void copyBtnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void delBtnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        serverPC.setNettyClient(nettyClient);
    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
