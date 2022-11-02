package org.vinogradov.myclient.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.mydto.commonClasses.FileInfo;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    private ClientLogic clientLogic;

    private boolean transfer;

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
        clientLogic.exitUserClient();
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
            clientLogic.createSendFileRequest(dstPath, srcPath, selectedFile);
        }
    }

    @FXML
    public void delBtnAction(ActionEvent actionEvent) {
    }

    @FXML
    public void refresh(ActionEvent actionEvent) {
        clientPC.updateList(Paths.get(clientPC.getCurrentPath()));
        clientLogic.createGetListRequest(serverPC.getCurrentPath());
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
        serverPC.setClientLogic(clientLogic);
    }
}
