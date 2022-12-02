package org.vinogradov.myclient.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import org.vinogradov.common.commonClasses.FileInfo;
import org.vinogradov.myclient.GUI.ByteConverter;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class PanelBuilder {

    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void buildTable(TableView<FileInfo> filesTable) {
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
    }

    public void buildMouseHandler(Consumer<FileInfo> consumer, TableView<FileInfo> filesTable) {
        filesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FileInfo selectedFile = filesTable.getSelectionModel().getSelectedItem();
                if (mouseEvent.getClickCount() == 2 && selectedFile != null && mouseEvent.getButton().name()
                        .equals(MouseButton.PRIMARY.name())) {
                    consumer.accept(selectedFile);
                }
            }
        });
    }

    public void buildActions(TableView<FileInfo> filesTable) {
        filesTable.setRowFactory(new Callback<TableView<FileInfo>, TableRow<FileInfo>>() {
            @Override
            public TableRow<FileInfo> call(TableView<FileInfo> fileInfoTableView) {
                final TableRow<FileInfo> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem openInFileItem = new MenuItem("Копировать путь");
                final MenuItem sendFileItem = new MenuItem("Загрузить файл");
                final MenuItem deleteFileItem = new MenuItem("Удалить файл");
                openInFileItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        clientController.copyPath();
                    }
                });
                sendFileItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        clientController.copyBtnAction(actionEvent);
                    }
                });
                deleteFileItem.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        clientController.delBtnAction(actionEvent);
                    }
                });
                contextMenu.getItems().addAll(openInFileItem, sendFileItem, deleteFileItem);
                row.contextMenuProperty().bind(
                        Bindings.when(row.emptyProperty())
                                .then((ContextMenu) null)
                                .otherwise(contextMenu)
                );
                return row;
            }
        });
    }
}
