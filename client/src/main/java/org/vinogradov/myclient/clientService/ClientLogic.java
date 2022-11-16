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
import java.util.ArrayList;
import java.util.List;

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
                createClientGUI(responseReg.getUpdatePanel());
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
                createClientGUI(responseAuth.getUpdatePanel());
            });
        } else {
            Platform.runLater(AlertWindowsClass::showAuthFalse);
        }
    }

    @Override
    public void getHandingMessageList(GetListResponse responseList) {
        UpdatePanel updatePanel = responseList.getUpdatePanel();
        clientController.serverPC.updateList(updatePanel);
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

    public void createSendFileRequest(Path srcPath, Path dstPath, FileInfo selectedFile) {
        List<String> dstPaths = new ArrayList<>();
        long referenceSize = 0;
        FileInfo.FileType fileType = selectedFile.getType();
        switch (fileType) {
            case FILE -> {
                dstPaths.add(dstPath.toString());
                referenceSize = selectedFile.getSize();
            }

            case DIRECTORY -> {
                dstPaths = HelperMethods.generatePaths(srcPath, dstPath);
                referenceSize = HelperMethods.sumSizeFiles(srcPath);
            }
        }
        MetaDataRequest metaDataRequest = new MetaDataRequest(user, dstPaths,
                selectedFile.getFilename(), referenceSize);
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

    private void createClientGUI(UpdatePanel updatePanel) {
        this.clientGUI = new ClientGUI(clientLogic);
        this.clientController = clientGUI.getClientController();
        clientController.setClientLogic(clientLogic);
        clientController.serverPC.updateList(updatePanel);
    }


}
