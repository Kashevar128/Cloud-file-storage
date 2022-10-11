package org.vinogradov.myclient.clientLogic;

import javafx.application.Platform;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

import java.util.List;

public class ClientHandlerLogicImpl implements ClientHandlerLogic {

    private RegAuthGui regAuthGui;

    private NettyClient nettyClient;

    private ClientGUI clientGUI;

    private ClientController clientController;

    private List<String> currentList;

    public void getResultMessageReg(RegServerResponse responseReg) {
        if (responseReg.isRegComplete()) {
            Platform.runLater(() -> {
                regAuthGui.getStage().close();
                AlertWindowsClass.showRegComplete();
                nettyClient.setUser(responseReg.getUser());
                createClientGUI(responseReg.getStartList());
            });
        }
        else {
            Platform.runLater(AlertWindowsClass::showRegFalse);
        }
    }

    public void getResultMessageAuth(AuthServerResponse responseAuth) {
        if (responseAuth.isAuthComplete()) {
            Platform.runLater(() -> {
                regAuthGui.getStage().close();
                AlertWindowsClass.showAuthComplete();
                nettyClient.setUser(responseAuth.getUser());
                createClientGUI(responseAuth.getStartList());
            });
        }
        else {
            Platform.runLater(AlertWindowsClass::showAuthFalse);
        }
    }

    public void setRegAuthGui(RegAuthGui regAuthGui) {
        this.regAuthGui = regAuthGui;
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    private void createClientGUI(List<String> startList) {
            this.clientGUI = new ClientGUI();
            this.clientController = clientGUI.getClientController();
            clientController.setNettyClient(nettyClient);
            clientController.serverPC.updateList(startList);
    }

    public ClientGUI getClientGUI() {
        return clientGUI;
    }
}
