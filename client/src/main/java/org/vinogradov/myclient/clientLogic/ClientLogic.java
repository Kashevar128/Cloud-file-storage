package org.vinogradov.myclient.clientLogic;

import javafx.application.Platform;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.RegServerResponse;

import java.util.List;

public class ClientLogic implements ClientHandlerLogic {

    private RegAuthGui regAuthGui;

    private NettyClient nettyClient;

    private ClientGUI clientGUI;

    private ClientController clientController;

    private List<String> currentList;

    @Override
    public void getHandingMessageReg(RegServerResponse responseReg) {
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

    @Override
    public void getHandingMessageAuth(AuthServerResponse responseAuth) {
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

    @Override
    public void getHandingMessageList(GetListResponse responseList) {
        List<String>currentList = responseList.getCurrentList();
        clientController.serverPC.updateList(currentList);
    }

    @Override
    public void getHandingOperationBan() {
        Platform.runLater(AlertWindowsClass::showOperationBan);
    }

    @Override
    public void getHandingConnectionLimit() {
        Platform.runLater(()-> {
            AlertWindowsClass.showConnectionLimit();
            regAuthGui.getStage().close();});
    }

    public void closeClient() {
        nettyClient.exitClient();
    }

    public void createRegClientRequest(String name, String pass) {
        nettyClient.sendMessage(new RegClientRequest(new User(name, pass)));
    }

    public void createAuthClientRequest(String name, String pass) {
        nettyClient.sendMessage(new AuthClientRequest(new User(name, pass)));
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

    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
