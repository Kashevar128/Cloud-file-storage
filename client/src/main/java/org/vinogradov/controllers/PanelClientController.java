package org.vinogradov.controllers;


import javafx.fxml.Initializable;
import org.vinogradov.FileInfo;

import java.net.URL;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class PanelClientController implements Initializable, PanelController<Path> {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public FileInfo getSelectedFileInfo() {
        return null;
    }

    @Override
    public String getCurrentPath() {
        return null;
    }

    @Override
    public void delFile(Path srcPath) {

    }

    @Override
    public void updateList(Path path) {

    }

    @Override
    public String[] getStringListFiles() {
        return new String[0];
    }
}
