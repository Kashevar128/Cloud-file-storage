package org.vinogradov.myclient.GUI;

import javafx.application.Platform;
import javafx.stage.Stage;

import java.io.IOException;

public class StartGui {
    public static void main(String[] args) {
        Platform.startup(() -> {
            try {
                new RegAuthGui();
            } catch (IOException|InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
