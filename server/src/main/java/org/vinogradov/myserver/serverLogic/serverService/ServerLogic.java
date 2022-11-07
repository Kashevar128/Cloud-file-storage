package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.User;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;
import org.vinogradov.myserver.serverLogic.connectionsService.ConnectionsController;
import org.vinogradov.myserver.serverLogic.storageService.Storage;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServerLogic implements ServerHandlerLogic {

    private final ConnectionsController connectionsController;

    private final DataBase dataBase;

    private final Storage storage;

    private final List<ServerHandler> serverHandlers = new ArrayList<>();

    public ServerLogic() {
        try {
            this.connectionsController = new ConnectionsController();
            this.dataBase = new DataBaseImpl();
            this.storage = new Storage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendRegServerResponse(RegClientRequest regClient) {
        User user = regClient.getUser();
        boolean regComplete = dataBase.createUser(user);
        if (regComplete) {
            Path startList = startWorkingWithUser(user);
            sendMessage(user, new RegServerResponse(true, user, startList));
            return;
        }
        sendMessage(user, new RegServerResponse(false));
    }

    @Override
    public void sendAuthServerResponse(AuthClientRequest authClient) {
        User user = authClient.getUser();
        boolean authComplete = dataBase.auth(user);
        if (authComplete) {
            Path startList = startWorkingWithUser(user);
            sendMessage(user, new AuthServerResponse(true, user, startList));
            return;
        }
        sendMessage(user, new AuthServerResponse(false));
    }

    @Override
    public void sendListResponse(GetListRequest listRequest) {
        User user = listRequest.getUser();
        Path path = Paths.get(listRequest.getPath());
        sendMessage(user, new GetListResponse(path));
    }

    @Override
    public void getHandingStartPackageRequest(StartSendPackageRequest startSendPackageRequest) {
        User user = startSendPackageRequest.getUser();
        String path = startSendPackageRequest.getPathFile();
        Path parentPath = Paths.get(path).getParent();
        HelperMethods.createNewDirectoryRecursion(parentPath);
        connectionsController.addFileChannelUser(path);
        sendMessage(user, new StartSendPackageResponse());
    }

    @Override
    public void getHandingSendPackageRequest(SendPackageRequest sendPackageRequest) {
        User user = sendPackageRequest.getUser();
        byte[] bytes = sendPackageRequest.getPackagePart();
        String dstPath = sendPackageRequest.getDstPath();
        FileOutputStream fileOutputStream = connectionsController.getFileChannelUser(dstPath);
        try {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sendMessage(user, new SendPackageResponse());
    }

    @Override
    public void getHandingStopPackageRequest(StopSendPackageRequest stopSendPackageRequest) {
        User user = stopSendPackageRequest.getUser();
        String dstPath = stopSendPackageRequest.getDstPath();
        connectionsController.stopFileOutputStream(dstPath);
        sendMessage(user, new StopSendPackageResponse());
        sendMessage(user, new GetListResponse(Paths.get(dstPath).getParent()));
    }

    @Override
    public void getHandingDelFileRequest(DelFileRequest delFileRequest) {
        User user = delFileRequest.getUser();
        Path delFilePath = Paths.get(delFileRequest.getDelFilePath());
        HelperMethods.deleteUserFile(delFilePath);
        sendMessage(user, new GetListResponse(delFilePath.getParent()));
    }

    @Override
    public void getHandingCreateNewFolderRequest(CreateNewFolderRequest createNewFolderRequest) {
        User user = createNewFolderRequest.getUser();
        Path path = Paths.get(createNewFolderRequest.getPathFolder());
        HelperMethods.createNewUserFile(path);
        sendMessage(user, new GetListResponse(path.getParent()));
    }


    public boolean filterSecurity(BasicQuery basicQuery) {
        User user = basicQuery.getUser();
        if (basicQuery instanceof AuthClientRequest
                || basicQuery instanceof RegClientRequest) return true;
        if (connectionsController.security(user)) {
            return true;
        }
        return false;
    }

    public void channelCollector(BasicQuery basicQuery, ChannelHandlerContext context) {
        if (basicQuery instanceof AuthClientRequest
                || basicQuery instanceof RegClientRequest) {
            connectionsController.putChannel(basicQuery, context);
        }
    }

    public void addConnectionLimit(ChannelHandlerContext context) {
        connectionsController.newConnectionLimit(context);
    }

    public void deleteUserConnection(ChannelHandlerContext context) {
        connectionsController.unConnectUser(context);
    }

    public void unConnectDataBase() {
        dataBase.closeDataBase();
    }


    private void sendMessage(User user, BasicQuery basicQuery) {
        Channel channel = connectionsController.getUserChannel(user.getNameUser());
        channel.writeAndFlush(basicQuery);
    }

    private Path startWorkingWithUser(User user) {
        connectionsController.stopTimerConnectionLimit(user);
        connectionsController.addUserInTemporaryDataBase(user);
        return storage.createUserRepository(user.getNameUser());
    }


    public List<ServerHandler> getServerHandlers() {
        return serverHandlers;
    }
}
