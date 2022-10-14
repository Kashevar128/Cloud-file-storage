package org.vinogradov.myclient.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.clientLogic.NettyClient;
import org.vinogradov.mydto.FileInfo;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.SendFileRequest;
import org.vinogradov.mysupport.HelperMethods;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class ClientController implements Initializable {

    private NettyClient nettyClient;

    private boolean transfer;

    Consumer<byte[]> copyConsumer;

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

        if (clientPC.getSelectedFileInfo() == null && serverPC.getSelectedFileInfo() == null) {
            AlertWindowsClass.showSelectFileAlert();
            return;
        }

        if (clientPC.getSelectedFileInfo() != null) {
            srcPC = clientPC;
            dstPC = serverPC;
            transfer = true;
        }

        if (serverPC.getSelectedFileInfo() != null) {
            srcPC = serverPC;
            dstPC = clientPC;
            transfer = false;
        }

        FileInfo selectedFile = srcPC.getSelectedFileInfo();
        srcPath = Paths.get(srcPC.getCurrentPath(), selectedFile.getFilename());
        dstPath = Paths.get(dstPC.getCurrentPath(), selectedFile.getFilename());

        if (transfer) {
            copyConsumer = bytes -> nettyClient.sendMessage(
                    new SendFileRequest(dstPath.toString(), selectedFile, bytes, nettyClient.getUser()));
            HelperMethods.saw(srcPath, copyConsumer);
        }
    }

    @FXML
    public void delBtnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        clientPC.updateList(Paths.get(clientPC.getCurrentPath()));
        nettyClient.sendMessage(new GetListRequest(nettyClient.getUser(), serverPC.getCurrentPath()));
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        serverPC.setNettyClient(nettyClient);
    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
