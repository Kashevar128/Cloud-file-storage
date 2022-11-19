package org.vinogradov.myclient.clientService;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import org.vinogradov.common.commonClasses.*;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;
import org.vinogradov.myclient.downloadService.DownloadController;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ClientLogic implements ClientHandlerLogic {

    private ChannelHandlerContext context;

    private RegAuthGui regAuthGui;

    private NettyClient nettyClient;

    private ClientGUI clientGUI;

    private ClientController clientController;

    private ClientLogic clientLogic;

    private User user;

    private DownloadController downloadController;

    public ClientLogic() {
        this.downloadController = new DownloadController();
    }

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

    @Override
    public void getHandingMetaDataResponse(MetaDataFileResponse metaDataFileResponse) {
        String fileName = metaDataFileResponse.getFileName();
        boolean allowTransmission = metaDataFileResponse.isAllowTransmission();
        if (allowTransmission) {
            BiConsumer<Long, byte[]> biConsumerSendPartFile = (id, bytes) -> {
                sendMessage(new SendPartFileRequest(user, id, fileName, bytes));
            };
            Map<Long, String> pathsMapFile = downloadController.getPathsMapFile(fileName);
            for (Map.Entry<Long, String> entry : pathsMapFile.entrySet()) {
                HelperMethods.split(entry.getKey(), entry.getValue(), biConsumerSendPartFile);
            }
        }
        downloadController.removeFileFromQueue(fileName);
    }

    public void closeClient() {
        nettyClient.exitClient();
    }

    public void createRegClientRequest(String name, String pass) {
        sendMessage(new RegClientRequest(new User(name, pass)));
    }

    public void createAuthClientRequest(String name, String pass) {
        sendMessage(new AuthClientRequest(new User(name, pass)));
    }

    public void createSendFileRequest(Path srcPath, Path dstPath, FileInfo selectedFile) {
        FileInfo.FileType fileType = selectedFile.getType();
        String fileName = selectedFile.getFilename();
        downloadController.addNewFileInQueue(fileName);
        Map<Long, String> dstPathsFile = new HashMap<>();
        Path parentDirectory = dstPath.getParent();
        long sizeFile = 0;

        switch (fileType) {

            case FILE -> {
                sizeFile = selectedFile.getSize();
                long idFile = downloadController.addFilePath(fileName, srcPath.toString());
                dstPathsFile.put(idFile, dstPath.toString());
            }

            case DIRECTORY -> {
                sizeFile = HelperMethods.sumSizeFiles(srcPath);
                Map<String, String> srcDstMap = HelperMethods.creatDstPaths(srcPath, dstPath);
                for (Map.Entry<String, String> entry : srcDstMap.entrySet()) {
                    long idFile = downloadController.addFilePath(fileName, entry.getKey());
                    dstPathsFile.put(idFile, entry.getValue());
                }
            }
        }
        sendMessage(new MetaDataFileRequest(user, fileName, dstPathsFile,
                parentDirectory.toString(), sizeFile));
    }

    public void createGetListRequest(String currentPath) {
        sendMessage(new GetListRequest(user, currentPath));
    }

    public void createDelFileRequest(String delFilePath) {
        sendMessage(new DelFileRequest(user, delFilePath));
    }

    public void createUserFolder(Path path) {
        sendMessage(new CreateNewFolderRequest(user, path.toString()));
    }

    public void sendMessage(BasicQuery basicQuery) {
        context.writeAndFlush(basicQuery);
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

    public void setContext(ChannelHandlerContext context) {this.context = context;}

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
