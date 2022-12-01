package org.vinogradov.myclient.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.vinogradov.common.commonClasses.CounterFileSize;

public class ProgressBarSendFile {

    private boolean end;
    private CounterFileSize counterFileSize;
    private final Stage primaryStage;
    private final ProgressBar progressBar;
    private final Label statusLabel;
    private Label startLoad;
    private final Button cancelButton;

    public ProgressBarSendFile() {
        this.end = false;

        this.counterFileSize = counterFileSize;
        this.primaryStage = new Stage();
        this.progressBar = new ProgressBar(0);
        final Label label = new Label("Copy files:");

        cancelButton = new Button("Cancel");

        this.statusLabel = new Label();
        statusLabel.setMinWidth(250);
        statusLabel.setTextFill(Color.BLUE);

        this.startLoad = new Label();

        // Cancel button
        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!checkSize()) {
                    setEnd(true);
                    Platform.runLater(AlertWindowsClass::showInterruptedFileTransferAlert);
                }
                primaryStage.close();
            }
        });

        FlowPane root = new FlowPane();
        root.setPadding(new Insets(10));
        root.setHgap(10);

        root.getChildren().addAll(label, progressBar, startLoad, //
                statusLabel, cancelButton);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
            }
        });
        primaryStage.setResizable(false);

        Scene scene = new Scene(root, 500, 80, Color.WHITE);
        primaryStage.setTitle("Transfer files");
        primaryStage.setScene(scene);
    }

    public void updateProgressBar(double progressMeaning) {
        progressBar.setProgress(progressMeaning);
        if (checkSize()) {
            Platform.runLater(this::makeFinishLabel);
        }
    }

    public void updateFileNameBar(String fileName, String direction) {
        Platform.runLater(() -> statusLabel.setText(direction + fileName));
    }

    private boolean checkSize() {
        return counterFileSize.getComparisonResult();
    }

    public void showProgressBar() {
        Platform.runLater(() -> {
            primaryStage.show();
            makeStartLabel();
        });
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

    private void makeStartLabel() {
        startLoad.setTextFill(Color.RED);
        startLoad.setText("Loading...");
    }

    private void makeFinishLabel() {
        startLoad.setTextFill(Color.GREEN);
        startLoad.setText("DONE!");
    }
}
