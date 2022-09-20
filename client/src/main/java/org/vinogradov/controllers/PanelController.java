package org.vinogradov.controllers;

import org.vinogradov.FileInfo;

import java.nio.file.Path;


public interface PanelController<T> {

    FileInfo getSelectedFileInfo();

    String getCurrentPath();

    void delFile(Path srcPath);

    void updateList(T t);

    String[] getStringListFiles();
}
