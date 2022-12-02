package org.vinogradov.myserver.serverLogic.consoleService;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.storageService.Storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ConsoleLogic {

    private final DisplayingInformation displayingInformation;
    private final DataBase dataBase;
    private final Storage storage;

    public ConsoleLogic(DataBase dataBase, Storage storage) {
        displayingInformation = new DisplayingInformation();
        this.dataBase = dataBase;
        this.storage = storage;
    }

    public void openServerStorage() {
        try {
            Path rootServerPath = Paths.get("server/Data_Storage");
            List<Path> startList = Files.list(rootServerPath)
                    .map(Path::getFileName).toList();
            displayingInformation.showNameFiles(rootServerPath, startList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
