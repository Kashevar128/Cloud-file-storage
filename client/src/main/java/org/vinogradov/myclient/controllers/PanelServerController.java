package org.vinogradov.myclient.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.vinogradov.common.commonClasses.UpdatePanel;
import org.vinogradov.common.responses.GetListResponse;
import org.vinogradov.myclient.GUI.ByteConverter;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.common.commonClasses.FileInfo;
import org.vinogradov.common.commonClasses.HelperMethods;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PanelServerController implements Initializable, PanelController<UpdatePanel> {

    private ClientController clientController;

    private String stringCurrentPath;

    private ClientLogic clientLogic;

    private PanelBuilder panelBuilder;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
        panelBuilder.setClientController(clientController);
    }

    @FXML
    public TableView<FileInfo> filesTable;

    @FXML
    public TextField pathField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelBuilder = new PanelBuilder();
        panelBuilder.buildTable(filesTable);

        Consumer<FileInfo> consumerMouseServer = (selectedFile) -> {
            Path path = Paths.get(getCurrentPath()).resolve(selectedFile.getFilename());
            if (selectedFile.getType() == FileInfo.FileType.DIRECTORY) {
                clientLogic.createGetListRequest(path.toString());
            }
        };

        panelBuilder.buildMouseHandler(consumerMouseServer, filesTable);

        panelBuilder.buildActions(filesTable);
    }

    @Override
    public FileInfo getSelectedFileInfo() {
        if (!filesTable.isFocused()) {
            return null;
        }
        return filesTable.getSelectionModel().getSelectedItem();
    }

    @Override
    public String getCurrentPath() {
        return stringCurrentPath;
    }

    @Override
    public void delFile(Path srcPath) {
        clientLogic.createDelFileRequest(srcPath.toString());
    }

    @Override
    public void updateList(UpdatePanel updatePanel) {
        String currentPath = updatePanel.getPath();
        List<FileInfo> fileInfos = updatePanel.getListInfo();
        pathField.setText(currentPath);
        stringCurrentPath = currentPath;
        filesTable.getItems().clear();
        filesTable.getItems().addAll(fileInfos);
        filesTable.sort();
    }

    @Override
    public void createNewPackage(String nameFolder) {
        Path path = Paths.get(getCurrentPath()).resolve(nameFolder);
        clientLogic.createUserFolder(path);
    }

    @Override
    public boolean getSelectedTable() {
        if (!filesTable.isFocused()) return false;
        return true;
    }

    @Override
    public void copyPathInBuffer(String srcPath) {
        ClipboardContent content = new ClipboardContent();
        content.putString(srcPath);
        Clipboard.getSystemClipboard().setContent(content);
    }

    @FXML
    public void btnPathBack(ActionEvent actionEvent) {
        Path backPath = Paths.get(getCurrentPath()).getParent();
        if (backPath != null) {
            clientLogic.createGetListRequest(backPath.toString());
        }
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }
}
