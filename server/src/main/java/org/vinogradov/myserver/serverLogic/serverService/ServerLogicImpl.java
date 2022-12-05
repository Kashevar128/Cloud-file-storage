package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.*;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;
import org.vinogradov.myserver.serverLogic.connectionService.ConverterPath;
import org.vinogradov.myserver.serverLogic.receivingFileServerService.ReceivingFileServerController;
import org.vinogradov.myserver.serverLogic.connectionService.ConnectionsController;
import org.vinogradov.myserver.serverLogic.sendFileServerService.SendFileServerController;
import org.vinogradov.myserver.serverLogic.storageService.Storage;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ServerLogicImpl implements ServerLogic {

    private ChannelHandlerContext context;

    private final ConnectionsController connectionsController;

    private final ReceivingFileServerController receivingFileServerController;

    private final SendFileServerController sendFileServerController;

    private final DataBase dataBase;

    private final Storage storage;

    private final NettyServer nettyServer;

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public ServerLogicImpl(DataBase dataBase, Storage storage, NettyServer nettyServer) {
        this.dataBase = dataBase;
        this.storage = storage;
        this.nettyServer = nettyServer;
        this.connectionsController = new ConnectionsController();
        this.receivingFileServerController = new ReceivingFileServerController();
        this.sendFileServerController = new SendFileServerController();
    }

    @Override
    public void getHandingRegOrAuthClientRequest(RegOrAuthClientRequest regOrAuthClientRequest) {
        User user = regOrAuthClientRequest.getUser();
        StatusUser statusUser = regOrAuthClientRequest.getStatusUser();
        boolean complete = false;
        switch (statusUser) {
            case REG -> complete = dataBase.createUser(user);
            case AUTH -> complete = dataBase.auth(user);
        }
        if (complete) {
            boolean addUser = nettyServer.getUserContextRepository()
                    .addUserChannelHandlerContextMap(user.getNameUser(), context);
            if (!addUser) {
                sendMessage(new TheUserIsAlreadyLoggedIn());
                return;
            }
        }
        Path startList = startWorkingWithUser(user);
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(startList.toString(), false);
        String pathStartListEdit = converterPath.getClientPathString();
        sendMessage(new RegOrAuthServerResponse(complete, pathStartListEdit, startList, statusUser, user));
    }

    @Override
    public void getHandingGetListRequest(GetListRequest listRequest) {
        String clientPath = listRequest.getPath();
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(clientPath, true);
        String pathRestoring = converterPath.getServerPathString();
        if (!pathRestoring.contains(converterPath.getNameRootDirectoryUser())) return;
        sendMessage(new GetListResponse(converterPath.getClientPathString(), converterPath.getServerPathToPath()));
    }

    @Override
    public void getHandingDelFileRequest(DelFileRequest delFileRequest) {
        String clientDelFilePath = delFileRequest.getDelFilePath();
        long sizeFile;
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(clientDelFilePath, true);
        Path serverPath = converterPath.getServerPathToPath();
        if (!Files.exists(serverPath)) return;
        if (Files.isDirectory(serverPath)) sizeFile = HelperMethods.sumSizeFiles(serverPath);
        else {
            try {
                sizeFile = Files.size(serverPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        connectionsController.getCloudUser().takeAwaySize(sizeFile);
        HelperMethods.deleteUserFile(converterPath.getServerPathToPath());
        sendMessage(new GetListResponse(converterPath.getParentClientPathString(),
                converterPath.getParentServerPathToPath()));
    }

    @Override
    public void getHandingCreateNewFolderRequest(CreateNewFolderRequest createNewFolderRequest) {
        String clientPathFolder = createNewFolderRequest.getPathFolder();
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(clientPathFolder, true);
        boolean youCanCreateTheFollowingDirectory = HelperMethods.
                countNextDirectory(converterPath.getClientPathString());
        if (!youCanCreateTheFollowingDirectory) {
            sendMessage(new NotCreateNewPathResponse());
            return;
        }
        HelperMethods.createNewUserFile(converterPath.getServerPathToPath());
        sendMessage(new GetListResponse(converterPath.getParentClientPathString(),
                converterPath.getParentServerPathToPath()));
    }

    @Override
    public void getHandingMetaDataFileRequest(MetaDataFileRequest metaDataFileRequest) {
        long sizeFile = metaDataFileRequest.getSizeFile();
        boolean notEnoughSpace = connectionsController.predictTheSizeCloud(sizeFile);
        if (notEnoughSpace) {
            sendMessage(new PermissionToTransferResponse(false));
            sendMessage(new ClearClientMapResponse());
            return;
        }
        connectionsController.addSizeCloud(sizeFile);
        String parentPath = metaDataFileRequest.getParentDirectory();
        Map<Long, String> dstPathsMap = metaDataFileRequest.getDstPathsMap();

        receivingFileServerController.createCounterFileSize(sizeFile);
        receivingFileServerController.addParentDirectoryPath(parentPath);
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPathsMap(dstPathsMap, true);
        receivingFileServerController.addFileOutputStreamMap(converterPath.getServerPathsMap());
        sendMessage(new PermissionToTransferResponse(true));
    }

    @Override
    public void getHandingSendPartFileRequest(SendPartFileRequest sendPartFileRequest) {
        Long idFile = sendPartFileRequest.getId();
        long sizePart = sendPartFileRequest.getSizePart();
        byte[] bytes = sendPartFileRequest.getBytes();

        receivingFileServerController.addSizePartInCounter(sizePart);
        receivingFileServerController.addBytesInFileOutputStream(idFile, bytes);
        boolean fileCheckSize = receivingFileServerController.sizeFileCheck();
        if (fileCheckSize) {
            String parentDirectoryPath = receivingFileServerController.getParentDirectoryPath();
            ConverterPath converterPath = connectionsController.getConverterPath();
            converterPath.setPath(parentDirectoryPath, true);
            sendMessage(new GetListResponse(converterPath.getClientPathString(),
                    converterPath.getServerPathToPath()));
            receivingFileServerController.closeAllFileOutputStreams();
        }
    }

    @Override
    public void getHandingClearFileOutputStreamsRequest(ClearFileOutputStreamsRequest clearFileOutputStreamsRequest) {
        receivingFileServerController.closeAllFileOutputStreams();
    }

    @Override
    public void getHandingGetFileRequest(GetFileRequest getFileRequest) {
        FileInfo.FileType fileType = getFileRequest.getFileType();
        String srcPathString = getFileRequest.getSrcPath();
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(srcPathString, true);
        String dstPathString = getFileRequest.getDstPath();
        Path srcPath = converterPath.getServerPathToPath();
        Path dstPath = Paths.get(dstPathString);
        Map<Long, String> dstPathsMap = new HashMap<>();
        long size = 0;
        switch (fileType) {
            case FILE -> {
                try {
                    size = Files.size(srcPath);
                    long id = sendFileServerController.addNewSrcPath(converterPath.getServerPathString());
                    dstPathsMap.put(id, dstPathString);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case DIRECTORY -> {
                Map<String, String> srcDstPaths = new HashMap<>();
                size = HelperMethods.sumSizeFiles(srcPath);
                srcDstPaths = HelperMethods.creatDstPaths(srcPath, dstPath);
                for (Map.Entry<String, String> entry : srcDstPaths.entrySet()) {
                    long id = sendFileServerController.addNewSrcPath(entry.getKey());
                    dstPathsMap.put(id, entry.getValue());
                }
            }
        }
        sendMessage(new MetaDataResponse(dstPathsMap, size));
    }

    @Override
    public void getHandingPermissionToTransferRequest(PermissionToTransferRequest permissionToTransferRequest) {
        boolean allowTransmission = permissionToTransferRequest.isAllowTransmission();
        if (allowTransmission) {
            MyFunction<Long, byte[], Boolean> myFunctionSendPartFile = (id, bytes) -> {
                sendMessage(new SendPartFileResponse(id, bytes));
                return false;
            };
            Map<Long, String> srcPathsMap = sendFileServerController.getSrcPathsMap();
            for (Map.Entry<Long, String> entry : srcPathsMap.entrySet()) {
                HelperMethods.splitFile(entry.getKey(), entry.getValue(), myFunctionSendPartFile);
            }
            sendFileServerController.clearSrcPathsMap();
        }
    }

    @Override
    public void getHandingPatternMatchingRequest(PatternMatchingRequest patternMatchingRequest) {
        User user = patternMatchingRequest.getUser();
        Field field = connectionsController.patternMatching(user.getNameUser(), user.getPassword());
        StatusUser statusUser = patternMatchingRequest.getStatusUser();
        if (field == null) {
            sendMessage(new PatternMatchingResponse(field, user, statusUser));
        } else sendMessage(new PatternMatchingResponse(field));
    }

    @Override
    public void getHandingOverwriteFileRequest(OverwriteFileRequest overwriteFileRequest) {
        String dstPath = overwriteFileRequest.getDstPath();
        ConverterPath converterPath = connectionsController.getConverterPath();
        converterPath.setPath(dstPath, true);
        boolean exists = Files.exists(converterPath.getServerPathToPath());
        if (exists) sendMessage(new OverwriteFileResponse(true));
        else sendMessage(new OverwriteFileResponse(false));
    }

    public boolean filterSecurity(BasicQuery basicQuery) {
        User user = basicQuery.getUser();
        if(basicQuery instanceof PatternMatchingRequest
                || basicQuery instanceof RegOrAuthClientRequest) return true;
        if (connectionsController.security(user)) {
            return true;
        }
        return false;
    }

    public void addConnectionLimit(ChannelHandlerContext context) {
        connectionsController.newConnectionLimit(context);
    }

    public void createTheUserIsAlreadyLoggedIn() {
        sendMessage(new TheUserIsAlreadyLoggedIn());
    }

    private void sendMessage(BasicQuery basicQuery) {
        context.writeAndFlush(basicQuery);
    }

    private Path startWorkingWithUser(User user) {
        connectionsController.stopTimerConnectionLimit();
        connectionsController.addDataUser(user);
        Path userPath = storage.createUserRepository(user.getNameUser());
        connectionsController.setConverterPath(userPath.toString());
        connectionsController.setCloudUser(storage.getCloudUser(user.getNameUser()));
        return userPath;
    }

    public ReceivingFileServerController getReceivingFileServerController() {
        return receivingFileServerController;
    }

    public SendFileServerController getSendFileServerController() {
        return sendFileServerController;
    }
}
