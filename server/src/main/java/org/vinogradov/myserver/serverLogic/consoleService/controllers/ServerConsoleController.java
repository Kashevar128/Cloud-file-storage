package org.vinogradov.myserver.serverLogic.consoleService.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.vinogradov.myserver.serverLogic.consoleService.GUI.ServerGUI;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerConsoleController implements Initializable {
    private final String waitingStr = "Введите <help> для помощи. Ожидание команды:";
    private final String lineStr = "------------------------------------------------------ + \n";
    public ServerGUI serverGUI;

    @FXML
    public TextArea outConsole;
    public TextField inConsole;

    public void setLog(String txt) {
        Platform.runLater(()-> {
            outConsole.appendText(txt + "\n");
        });
    }

    public void clearLog() {
        outConsole.clear();
        outConsole.setText(waitingStr);
    }

    public void exit() {
        serverGUI.getStage().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inConsole.setOnAction(actionEvent -> {
            if (!inConsole.getText().equals("clear")) {
                setLog("Вы ввели команду: " + inConsole.getText());
            }
            serverGUI.getExecuteTheCommand().execute(inConsole.getText());
            if (inConsole.getText().equals("clear")) {
                inConsole.clear();
                return;
            }
            inConsole.clear();
            setLog(lineStr);
            writeWaitingMsg();
        });
        outConsole.setEditable(false);
        setLog("Start server...\n" + "----------------------------------------------------------------------");
        setLog(waitingStr);
    }

    public void writeWaitingMsg() {
        setLog(waitingStr);
    }

    public void setServerGUI(ServerGUI serverGUI) {
        this.serverGUI = serverGUI;
    }
}
