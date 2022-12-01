package org.vinogradov.myclient.controllers;

import org.vinogradov.common.commonClasses.FileInfo;

import java.nio.file.Path;


public interface PanelController<T> {

    FileInfo getSelectedFileInfo();

    String getCurrentPath();

    void delFile(Path srcPath);

    void updateList(T t);

    void createNewPackage(String nameFolder);

    boolean getSelectedTable();
}
