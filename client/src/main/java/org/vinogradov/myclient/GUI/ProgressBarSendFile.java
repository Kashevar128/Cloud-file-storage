package org.vinogradov.myclient.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.vinogradov.common.commonClasses.CounterFileSize;
import org.vinogradov.myclient.controllers.ClientController;

public class ProgressBarSendFile {

    private boolean end;
    private CounterFileSize counterFileSize;
    private final ClientController clientController;
    private final Stage primaryStage;
    private final ProgressBar progressBar;
    private final Label statusLabel;
    private final ProgressIndicator progressIndicator;
    private final Button cancelButton;

    public ProgressBarSendFile(ClientController clientController) {
        this.end = false;

        this.counterFileSize = counterFileSize;
        this.clientController = clientController;
        this.primaryStage = new Stage();
        this.progressBar = new ProgressBar(0);
        this.progressIndicator = new ProgressIndicator(0);
        final Label label = new Label("Copy files:");

        cancelButton = new Button("Cancel");

        this.statusLabel = new Label();
        statusLabel.setMinWidth(250);
        statusLabel.setTextFill(Color.BLUE);

        // Cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!checkSize()) {
                    setEnd(true);
                }
                primaryStage.close();
                clientController.getSendFileButton().setDisable(false);
            }
        });

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);

        root.getChildren().addAll(label, progressBar, progressIndicator, //
                statusLabel, cancelButton);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, 500, 120, Color.WHITE);
        primaryStage.setTitle("Send files");
        primaryStage.setScene(scene);
    }

    public void updateProgressBar(double progressMeaning) {
        progressBar.setProgress(progressMeaning);
        progressIndicator.setProgress(progressMeaning);
    }

    public void updateFileNameBar(String fileName, String direction) {
        Platform.runLater(() -> statusLabel.setText(direction + fileName));
    }

    private boolean checkSize() {
        return counterFileSize.getComparisonResult();
    }

    public void showProgressBar() {
        Platform.runLater(primaryStage::show);
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean flag) {
        this.end = flag;
    }

    public void setCounterFileSize(CounterFileSize counterFileSize) {
        this.counterFileSize = counterFileSize;
    }
}
