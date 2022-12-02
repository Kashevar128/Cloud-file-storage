package org.vinogradov.myclient.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.*;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.common.commonClasses.FileInfo;
import org.vinogradov.common.commonClasses.HelperMethods;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class PanelClientController implements Initializable, PanelController<Path> {

    private ClientController clientController;

    PanelBuilder panelBuilder;

    private String currentPath;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
        panelBuilder.setClientController(clientController);
    }

    @FXML
    public TableView<FileInfo> filesTable;
    public ComboBox<String> disksBox;
    public TextField pathField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panelBuilder = new PanelBuilder();
        panelBuilder.buildTable(filesTable);

        disksBox.getItems().clear();
        for (Path p : FileSystems.getDefault().getRootDirectories()) {
            disksBox.getItems().add(p.toString());
        }
        disksBox.getSelectionModel().select(0);

        Consumer<FileInfo> consumerMouseClient = (selectedFile) -> {
            Path path = Paths.get(getCurrentPath()).resolve(selectedFile.getFilename());
            if (Files.isDirectory(path)) {
                updateList(path);
            }
        };

        panelBuilder.buildMouseHandler(consumerMouseClient, filesTable);

        panelBuilder.buildActions(filesTable);

        updateList(Paths.get(System.clearProperty("user.home")));
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
        return currentPath;
    }

    @Override
    public void delFile(Path path) {
        if (HelperMethods.deleteUserFile(path)) {
            updateList(path.getParent());
            return;
        }
        Platform.runLater(AlertWindowsClass::showDelFileError);
    }

    @Override
    public void updateList(Path path) {
        try {
            Path pathNorm = path.toAbsolutePath().normalize();
            pathField.setText(pathNorm.toString());
            currentPath = pathNorm.toString();
            filesTable.getItems().clear();
            filesTable.getItems().addAll(Files.list(pathNorm).map(FileInfo::new).collect(Collectors.toList()));
            filesTable.sort();
        } catch (IOException e) {
            Platform.runLater(AlertWindowsClass::showUpdateListError);
            e.printStackTrace();
        }
    }

    @Override
    public void createNewPackage(String nameFolder) {
        Path path = Paths.get(getCurrentPath()).resolve(nameFolder);
        HelperMethods.createNewUserFile(path);
        updateList(Paths.get(getCurrentPath()));
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
    public void selectDiskAction(ActionEvent actionEvent) {
        ComboBox<String> element = (ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(element.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void btnPathBack(ActionEvent actionEvent) {
        Path backPath = Paths.get(pathField.getText()).getParent();
        if (backPath != null) {
            updateList(backPath);
        }
    }


}
