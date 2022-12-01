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
import java.util.stream.Collectors;

public class PanelServerController implements Initializable, PanelController<UpdatePanel> {

    private String stringCurrentPath;

    private ClientLogic clientLogic;

    @FXML
    public TableView<FileInfo> filesTable;

    @FXML
    public TextField pathField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getType().getName()));
        fileTypeColumn.setPrefWidth(24);

        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Имя");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFilename()));
        fileNameColumn.setPrefWidth(140);

        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Размер");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        fileSizeColumn.setCellFactory(column -> {
            return new TableCell<FileInfo, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        ByteConverter byteConverter = new ByteConverter(item);
                        String text = byteConverter.getSizeFileStringFormat();
                        if (item == -1L) {
                            text = "[DIR]";
                        }
                        setText(text);
                    }
                }
            };
        });
        fileSizeColumn.setPrefWidth(120);

        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> fileDateColumn = new TableColumn<>("Дата изменения");
        fileDateColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastModified().format(dft)));
        fileDateColumn.setPrefWidth(120);

        filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileDateColumn);
        filesTable.getSortOrder().add(fileTypeColumn);

        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileInfo selectedFile = filesTable.getSelectionModel().getSelectedItem();
                if (mouseEvent.getClickCount() == 2 && selectedFile != null) {
                    Path path = Paths.get(getCurrentPath()).resolve(selectedFile.getFilename());
                    if (selectedFile.getType() == FileInfo.FileType.DIRECTORY) {
                        clientLogic.createGetListRequest(path.toString());
                    }
                }
            }
        });
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
