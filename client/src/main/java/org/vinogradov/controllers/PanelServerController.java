package org.vinogradov.controllers;

import javafx.fxml.Initializable;
import org.vinogradov.FileInfo;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.ResourceBundle;

public class PanelServerController implements Initializable, PanelController<List<String>> {


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
    public void updateList(List<String> strings) {

    }

    @Override
    public String[] getStringListFiles() {
        return new String[0];
    }
}
