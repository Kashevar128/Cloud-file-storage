package org.vinogradov.myclient.clientService;

import javafx.application.Platform;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.mydto.commonClasses.FileInfo;
import org.vinogradov.mydto.commonClasses.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.requests.SendFileRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.mysupport.HelperMethods;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;

public class ClientLogic implements ClientHandlerLogic {

    private RegAuthGui regAuthGui;

    private NettyClient nettyClient;

    private ClientGUI clientGUI;

    private ClientController clientController;

    private ClientLogic clientLogic;

    private User user;


    @Override
    public void getHandingMessageReg(RegServerResponse responseReg) {
        if (responseReg.isRegComplete()) {
            Platform.runLater(() -> {
                regAuthGui.getStage().close();
                AlertWindowsClass.showRegComplete();
                this.user = responseReg.getUser();
                createClientGUI(responseReg.getStartList());
            });
        } else {
            Platform.runLater(AlertWindowsClass::showRegFalse);
        }
    }

    @Override
    public void getHandingMessageAuth(AuthServerResponse responseAuth) {
        if (responseAuth.isAuthComplete()) {
            Platform.runLater(() -> {
                regAuthGui.getStage().close();
                AlertWindowsClass.showAuthComplete();
                this.user = responseAuth.getUser();
                createClientGUI(responseAuth.getStartList());
            });
        } else {
            Platform.runLater(AlertWindowsClass::showAuthFalse);
        }
    }

    @Override
    public void getHandingMessageList(GetListResponse responseList) {
        List<String> currentList = responseList.getCurrentList();
        clientController.serverPC.updateList(currentList);
    }

    @Override
    public void getHandingConnectionLimit() {
        Platform.runLater(() -> {
            AlertWindowsClass.showConnectionLimit();
            regAuthGui.getStage().close();
        });
    }

    public void closeClient() {
        nettyClient.exitClient();
    }

    public void createRegClientRequest(String name, String pass) {
        nettyClient.send(new RegClientRequest(new User(name, pass)));
    }

    public void createAuthClientRequest(String name, String pass) {
        nettyClient.send(new AuthClientRequest(new User(name, pass)));
    }

    public void exitUserClient() {
        nettyClient.exitClient();
    }

    public void createSendFileRequest(Path dstPath, Path srcPath, FileInfo selectedFile) {
        Consumer<byte[]> copyConsumer = bytes -> nettyClient.send(
                new SendFileRequest(dstPath.toString(), selectedFile, bytes, user));
        HelperMethods.saw(srcPath, copyConsumer);
    }

    public void createGetListRequest(String currentPath) {
        nettyClient.send(new GetListRequest(user, currentPath));
    }


    public void setRegAuthGui(RegAuthGui regAuthGui) {
        this.regAuthGui = regAuthGui;
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }

    public User getUser() {
        return user;
    }


    private void createClientGUI(List<String> startList) {
        this.clientGUI = new ClientGUI(clientLogic);
        this.clientController = clientGUI.getClientController();
        clientController.setClientLogic(clientLogic);
        clientController.serverPC.updateList(startList);
    }
}
