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

public class PanelServerController implements Initializable, PanelController<List<String>> {

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
                        String text = String.format("%,d bytes", item);
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
                if (mouseEvent.getClickCount() == 2) {
                    Path path = Paths.get(getCurrentPath()).resolve(filesTable.getSelectionModel().getSelectedItem().getFilename());
                    if (Files.isDirectory(path)) {
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
    public void updateList(List<String> list) {
        List<Path> listPath = list.stream().map(Paths::get).collect(Collectors.toList());
        Path currentPath = listPath.get(list.size() - 1);
        listPath.remove(list.size() - 1);
        pathField.setText(HelperMethods.editingPath(currentPath.toAbsolutePath(), clientLogic.getUser().getNameUser()));
        stringCurrentPath = currentPath.toAbsolutePath().toString();
        filesTable.getItems().clear();
        filesTable.getItems().addAll(listPath.stream().map(FileInfo::new).collect(Collectors.toList()));
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
        if (backPath != null && !backPath.toString().endsWith("Data_Storage")) {
            clientLogic.createGetListRequest(backPath.toString());
        }
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }
}
