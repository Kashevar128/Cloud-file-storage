package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.User;
import org.vinogradov.mydto.requests.*;
import org.vinogradov.mydto.responses.*;
import org.vinogradov.myserver.serverLogic.connectionsService.ConnectionsController;
import org.vinogradov.myserver.serverLogic.storageService.Storage;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.mysupport.HelperMethods;

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
            List<String> startList = startWorkingWithUser(user);
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
            List<String> startList = startWorkingWithUser(user);
            sendMessage(user, new AuthServerResponse(true, user, startList));
            return;
        }
        sendMessage(user, new AuthServerResponse(false));
    }

    @Override
    public void sendListResponse(GetListRequest listRequest) {
        User user = listRequest.getUser();
        Path path = Paths.get(listRequest.getPath());
        List<String> newList = HelperMethods.generateStringList(path);
        sendMessage(user, new GetListResponse(newList));
    }

    @Override
    public void getHandingStartSendFileRequest(StartSendFileRequest startSendFileRequest) {
        User user = startSendFileRequest.getUser();
        String path = startSendFileRequest.getPathFile();
        connectionsController.addFileChannelUser(path);
        sendMessage(user, new StartSendFileResponse());
    }

    @Override
    public void getHandingSendPartFileRequest(SendPartFileRequest sendPartFileRequest) {
        User user = sendPartFileRequest.getUser();
        byte[] bytes = sendPartFileRequest.getPackagePart();
        String dstPath = sendPartFileRequest.getDstPath();

        FileOutputStream fileOutputStream = connectionsController.getFileChannelUser(dstPath);
        try {
            fileOutputStream.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        Path filePath = dstPath.getParent();
//        List<String> newList = HelperMethods.generateStringList(filePath);
        sendMessage(user, new SendPartFileResponse());
    }

    @Override
    public void getHandingStopSendFileRequest(StopSendFileRequest stopSendFileRequest) {
        User user = stopSendFileRequest.getUser();
        String dstPath = stopSendFileRequest.getDstPath();
        connectionsController.stopFileOutputStream(dstPath);
        sendMessage(user, new StopSendFileResponse());
        sendMessage(user, new GetListResponse(HelperMethods.generateStringList(Paths.get(dstPath).getParent())));
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

    private List<String> startWorkingWithUser(User user) {
        connectionsController.stopTimerConnectionLimit(user);
        connectionsController.addUserInTemporaryDataBase(user);
        Path path = storage.createUserRepository(user.getNameUser());
        List<String> startList = HelperMethods.generateStringList(path);
        return startList;
    }


    public List<ServerHandler> getServerHandlers() {
        return serverHandlers;
    }
}
