package org.vinogradov.myclient.controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.EnterWindow;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.common.commonClasses.FileInfo;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public Button sendFileButton;
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
        clientLogic.closeClient();
    }

    @FXML
    public void copyBtnAction(ActionEvent actionEvent) {

        FileInfo selectedFile = selectFile();
        if (selectedFile == null) return;

        if (transfer) {
            clientLogic.createSendFileRequest(srcPath, dstPath, selectedFile);
        } else clientLogic.createGetFileRequest(srcPath, dstPath, selectedFile);
    }

    @FXML
    public void delBtnAction(ActionEvent actionEvent) {
        FileInfo selectedFile = selectFile();
        if (selectedFile == null) return;
        srcPC.delFile(srcPath);
    }

    @FXML
    public void refreshBtnAction(ActionEvent actionEvent) {
        clientPC.updateList(Paths.get(clientPC.getCurrentPath()));
        clientLogic.createGetListRequest(serverPC.getCurrentPath());
    }

    @FXML
    public void createPackageBtnAction(ActionEvent actionEvent) {
        if (selectTable()) {
            Platform.runLater(() -> new EnterWindow(clientLogic.getClientController(), clientLogic.getClientGUI()));
        }
    }

    private FileInfo selectFile() {

        if (clientPC.getSelectedFileInfo() == null && serverPC.getSelectedFileInfo() == null) {
            Platform.runLater(AlertWindowsClass::showSelectFileAlert);
            return null;
        }

        panelDistribution();

        FileInfo selectedFile = srcPC.getSelectedFileInfo();
        srcPath = Paths.get(srcPC.getCurrentPath(), selectedFile.getFilename());
        dstPath = Paths.get(dstPC.getCurrentPath(), selectedFile.getFilename());
        return selectedFile;
    }

    private boolean selectTable() {
        if (!clientPC.getSelectedTable() && !serverPC.getSelectedTable()) {
            Platform.runLater(AlertWindowsClass::showSelectTableAlert);
            return false;
        }

        panelDistribution();

        return true;
    }

    private void panelDistribution() {
        if (clientPC.getSelectedTable()) {
            srcPC = clientPC;
            dstPC = serverPC;
            transfer = true;
        }

        if (serverPC.getSelectedTable()) {
            srcPC = serverPC;
            dstPC = clientPC;
            transfer = false;
        }
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
        serverPC.setClientLogic(clientLogic);
    }

    public PanelController getSrcPC() {
        return srcPC;
    }

    public Button getSendFileButton() {
        return sendFileButton;
    }
}
