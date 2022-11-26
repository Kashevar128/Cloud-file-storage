package org.vinogradov.myclient.clientService;

import io.netty.channel.ChannelHandlerContext;
import javafx.application.Platform;
import org.vinogradov.common.commonClasses.*;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.GUI.ClientGUI;
import org.vinogradov.myclient.GUI.ProgressBarSendFile;
import org.vinogradov.myclient.GUI.RegAuthGui;
import org.vinogradov.myclient.controllers.ClientController;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;
import org.vinogradov.myclient.receivingFileClientService.ReceivingFileClientController;
import org.vinogradov.myclient.sendFileClientService.SendFileClientController;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


public class ClientLogic implements ClientHandlerLogic {

    private final Runnable runnableRegComplete = AlertWindowsClass::showRegComplete,
            runnableAuthComplete = AlertWindowsClass::showAuthComplete,
            runnableRegFalse = AlertWindowsClass::showRegFalse,
            runnableAuthFalse = AlertWindowsClass::showAuthFalse;

    private ChannelHandlerContext context;

    private RegAuthGui regAuthGui;

    private NettyClient nettyClient;

    private ClientGUI clientGUI;

    private ClientController clientController;

    private ClientLogic clientLogic;

    private User user;

    private ProgressBarSendFile progressBarSendFile;

    private final SendFileClientController sendFileClientController;

    private final ReceivingFileClientController receivingFileClientController;

    public ClientLogic() {
        this.receivingFileClientController = new ReceivingFileClientController();
        this.sendFileClientController = new SendFileClientController();
    }

    @Override
    public void getHandingMessageReg(RegServerResponse responseReg) {
        this.user = responseReg.getUser();
        UpdatePanel updatePanelReg = responseReg.getUpdatePanel();
        boolean regComplete = responseReg.isRegComplete();
        startClientGUI(regComplete, updatePanelReg, runnableRegComplete, runnableRegFalse);
    }

    @Override
    public void getHandingMessageAuth(AuthServerResponse responseAuth) {
        this.user = responseAuth.getUser();
        UpdatePanel updatePanelAuth = responseAuth.getUpdatePanel();
        boolean authComplete = responseAuth.isAuthComplete();
       startClientGUI(authComplete, updatePanelAuth, runnableAuthComplete, runnableAuthFalse);
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
    public void getHandingPermissionToTransferResponse(PermissionToTransferResponse permissionToTransferResponse) {
        boolean allowTransmission = permissionToTransferResponse.isAllowTransmission();
        if (allowTransmission) {
            String nameFileOrDirectorySend = sendFileClientController.getNameFileOrDirectorySend();
            progressBarSendFile.updateFileNameBar(nameFileOrDirectorySend);
            progressBarSendFile.setCounterFileSize(sendFileClientController.getCounterFileSize());
            progressBarSendFile.showProgressBar();
            MyFunction<Long, byte[], Boolean> myFunctionSendPartFile = (id, bytes) -> {
                sendFileClientController.addSizePartInCounter(bytes.length);
                sendMessage(new SendPartFileRequest(user, id, bytes));
                progressBarSendFile.updateProgressBar(sendFileClientController.getRatioCounter());
                if (progressBarSendFile.isEnd()) {
                    progressBarSendFile.setEnd(false);
                    sendMessage(new DelFileRequest(user, sendFileClientController.getSelectedDstPath()));
                    sendMessage(new ClearFileOutputStreamsRequest(user));
                    return true;
                }
                return false;
            };
            Map<Long, String> pathsMapFile = sendFileClientController.getMapSrcPaths();
            for (Map.Entry<Long, String> entry : pathsMapFile.entrySet()) {
                if (HelperMethods.split(entry.getKey(), entry.getValue(), myFunctionSendPartFile)) break;
            }
            sendFileClientController.clearSrcPathsMap();
        }
    }

    @Override
    public void getHandingMetaDataResponse(MetaDataResponse metaDataResponse) {
        long sizeFile = metaDataResponse.getSizeFile();
        Map<Long, String> dstPaths = metaDataResponse.getDstPaths();
        receivingFileClientController.addFileOutputStreamMap(dstPaths);
        receivingFileClientController.createCounterFileSize(sizeFile);
        sendMessage(new PermissionToTransferRequest(user, true));
    }

    @Override
    public void getHandingSendPartFileResponse(SendPartFileResponse sendPartFileResponse) {
        long sizePart = sendPartFileResponse.getSizePart();
        Long id = sendPartFileResponse.getId();
        byte[] bytes = sendPartFileResponse.getBytes();
        Path parentFilePath = Paths.get(receivingFileClientController.getDstPath()).getParent();
        receivingFileClientController.addSizePartInCounter(sizePart);
        receivingFileClientController.addBytesInFileOutputStream(id, bytes);
        boolean sizeFileCheck = receivingFileClientController.sizeFileCheck();
        if (sizeFileCheck) {
            receivingFileClientController.closeAllFileOutputStreams();
            clientController.clientPC.updateList(parentFilePath);
        }
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
        String fileOrDirectoryName = selectedFile.getFilename();
        sendFileClientController.setNameFileOrDirectorySend(fileOrDirectoryName);
        Path parentDirectory = dstPath.getParent();
        sendFileClientController.setSelectedDstPath(dstPath.toString());
        Map<Long, String> dstPathsMap = new HashMap<>();
        long sizeFile = 0;

        switch (fileType) {

            case FILE -> {
                sizeFile = selectedFile.getSize();
                Long id = sendFileClientController.addNewSrcPath(srcPath.toString());
                dstPathsMap.put(id, dstPath.toString());
            }

            case DIRECTORY -> {
                sizeFile = HelperMethods.sumSizeFiles(srcPath);
                Map<String, String> srcDstMap = HelperMethods.creatDstPaths(srcPath, dstPath);
                for (Map.Entry<String, String> entry : srcDstMap.entrySet()) {
                    Long id = sendFileClientController.addNewSrcPath(entry.getKey());
                    dstPathsMap.put(id, entry.getValue());
                }
            }
        }
        clientController.getSendFileButton().setDisable(true);
        sendFileClientController.createNewCounterFileSize(sizeFile);
        sendMessage(new MetaDataFileRequest(user, dstPathsMap,
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

    public void createGetFileRequest(Path srcPath, Path dstPath, FileInfo selectedFile) {
        receivingFileClientController.setDstPath(dstPath.toString());
        sendMessage(new GetFileRequest(user, selectedFile.getType(), srcPath.toString(), dstPath.toString()));
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

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
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

    private void startClientGUI(boolean complete, UpdatePanel updatePanel,
                                Runnable runnableComplete, Runnable runnableFalse) {
        if (complete) {
            Platform.runLater(() -> {
                regAuthGui.getStage().close();
                runnableComplete.run();
                this.clientGUI = new ClientGUI(clientLogic);
                this.clientController = clientGUI.getClientController();
                clientController.setClientLogic(clientLogic);
                clientController.serverPC.updateList(updatePanel);
                this.progressBarSendFile = new ProgressBarSendFile(clientController);
            });
        } else {
            runnableFalse.run();
        }
    }


}
