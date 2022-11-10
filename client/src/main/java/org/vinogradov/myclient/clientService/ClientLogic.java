package org.vinogradov.myclient.clientService;

import javafx.application.Platform;
import org.vinogradov.common.commonClasses.*;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;

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
        BiConsumer<Path, Path> biConsumer = (srcPath1, dstPath1) -> {
            Consumer<byte[]> sendFile = bytes -> nettyClient.send(
                    new SendPackageRequest(dstPath1.toString(), bytes, user)
            );
            nettyClient.send(new StartSendPackageRequest(dstPath1.toString(), user));
            HelperMethods.split(srcPath1, sendFile);
            nettyClient.send(new StopSendPackageRequest(dstPath1.toString(), user));
        };
        HelperMethods.sendFile(dstPath, srcPath, selectedFile, biConsumer);
    }

    public void createGetListRequest(String currentPath) {
        nettyClient.send(new GetListRequest(user, currentPath));
    }

    public void createDelFileRequest(String delFilePath) {
        nettyClient.send(new DelFileRequest(user, delFilePath));
    }

    public void createUserFolder(Path path) {
        nettyClient.send(new CreateNewFolderRequest(user, path.toString()));
    }

    public boolean filterMessage(BasicQuery basicQuery) {
        if (basicQuery instanceof StartSendPackageResponse ||
                basicQuery instanceof SendPackageResponse ||
                basicQuery instanceof StopSendPackageResponse) return false;
        return true;
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

    public ClientGUI getClientGUI() {
        return clientGUI;
    }

    public ClientController getClientController() {
        return clientController;
    }

    private void createClientGUI(List<String> startList) {
        this.clientGUI = new ClientGUI(clientLogic);
        this.clientController = clientGUI.getClientController();
        clientController.setClientLogic(clientLogic);
        clientController.serverPC.updateList(startList);
    }


}
