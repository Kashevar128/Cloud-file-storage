package org.vinogradov.myclient.starterClient;

import javafx.application.Platform;
import org.vinogradov.myclient.GUI.RegAuthGui;

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
