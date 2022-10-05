package org.vinogradov.myclient.controllers;

import org.vinogradov.mydto.FileInfo;

import java.nio.file.Path;


public interface PanelController<T> {

    FileInfo getSelectedFileInfo();

    String getCurrentPath();

    void delFile(Path srcPath);

    void updateList(T t);

    String[] getStringListFiles();
}
