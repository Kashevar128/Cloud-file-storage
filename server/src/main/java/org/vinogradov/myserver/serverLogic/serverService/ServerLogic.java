package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.common.commonClasses.BasicQuery;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.User;
import org.vinogradov.common.requests.*;
import org.vinogradov.common.responses.*;
import org.vinogradov.myserver.serverLogic.connectionService.ConnectionsController;
import org.vinogradov.myserver.serverLogic.storageService.Storage;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ServerLogic implements ServerHandlerLogic {

    private static final ConnectionsController connectionsController;

    private static final DataBase dataBase;

    private static final Storage storage;

    static {
        try {
            connectionsController = new ConnectionsController();
            dataBase = new DataBaseImpl();
            storage = new Storage();
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
            sendMessage(new RegServerResponse(true, startList, user));
            return;
        }
        sendMessage(new RegServerResponse(false));
    }

    @Override
    public void sendAuthServerResponse(AuthClientRequest authClient) {
        User user = authClient.getUser();
        boolean authComplete = dataBase.auth(user);
        if (authComplete) {
            Path startList = startWorkingWithUser(user);
            sendMessage(new AuthServerResponse(true, startList, user));
            return;
        }
        sendMessage(new AuthServerResponse(false));
    }

    @Override
    public void sendListResponse(GetListRequest listRequest) {
        Path path = Paths.get(listRequest.getPath());
        sendMessage(new GetListResponse(path));
    }

    @Override
    public void getHandingDelFileRequest(DelFileRequest delFileRequest) {
        Path delFilePath = Paths.get(delFileRequest.getDelFilePath());
        HelperMethods.deleteUserFile(delFilePath);
        sendMessage(new GetListResponse(delFilePath.getParent()));
    }

    @Override
    public void getHandingCreateNewFolderRequest(CreateNewFolderRequest createNewFolderRequest) {
        Path path = Paths.get(createNewFolderRequest.getPathFolder());
        HelperMethods.createNewUserFile(path);
        sendMessage(new GetListResponse(path.getParent()));
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
            connectionsController.putChannel(context);
        }
    }

    public void addConnectionLimit(ChannelHandlerContext context) {
        connectionsController.newConnectionLimit(context);
    }


    private void sendMessage(BasicQuery basicQuery) {
        connectionsController.getUserChannel().writeAndFlush(basicQuery);
    }

    private Path startWorkingWithUser(User user) {
        connectionsController.stopTimerConnectionLimit();
        connectionsController.addDataUser(user);
        return storage.createUserRepository(user.getNameUser());
    }

    public static void unConnectDataBase() {
        dataBase.closeDataBase();
    }

}
