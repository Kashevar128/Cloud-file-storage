package org.vinogradov.myserver.serverLogic.serverService;

import io.netty.channel.Channel;
import org.vinogradov.mydto.commonClasses.BasicQuery;
import org.vinogradov.mydto.commonClasses.FileInfo;
import org.vinogradov.mydto.commonClasses.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.requests.SendFileRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.OperationBanResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.myserver.serverLogic.ConnectionsService.ConnectionsController;
import org.vinogradov.myserver.serverLogic.storageService.Storage;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.mysupport.HelperMethods;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ServerLogic implements ServerHandlerLogic {

    private final ConnectionsController connectionsController;

    private final DataBase dataBase;

    private final Storage storage;

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
        boolean regComplete = dataBase.addUser(user);
        if (regComplete) {
            List<String> startList = gettingStart(user);
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
            List<String> startList = gettingStart(user);
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
    public void getHandingSendFileRequest(SendFileRequest sendFileRequest) {
        User user = sendFileRequest.getUser();
        byte[] bytes = sendFileRequest.getPackageByte();
        FileInfo.FileType fileType = sendFileRequest.getFileInfo().getType();
        Path dstPath = Paths.get(sendFileRequest.getDstPath());
        switch (fileType) {
            case FILE -> {
                try (FileOutputStream fileOutputStream = new FileOutputStream(dstPath.toFile())) {
                    fileOutputStream.write(bytes);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            case DIRECTORY -> {
                if (!Files.exists(dstPath)) {
                    try (FileOutputStream fileOutputStream = new FileOutputStream(dstPath.toFile())) {
                        Files.createDirectory(dstPath);
                        fileOutputStream.write(bytes);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        Path filePath = dstPath.getParent();
        List<String> newList = HelperMethods.generateStringList(filePath);
        sendMessage(user, new GetListResponse(newList));
    }

    private void sendMessage(User user, BasicQuery basicQuery) {
        Channel channel = connectionsController.getUserChannel(user.getNameUser());
        channel.writeAndFlush(basicQuery);
    }

    private void security(BasicQuery basicQuery) {
        User user = basicQuery.getUser();
        boolean auth = dataBase.auth(user);
        if (auth) return;
        sendMessage(user, new OperationBanResponse());
    }

    private List<String> gettingStart(User user) {
        connectionsController.stopConnectionTimer(user);
        Path path = storage.createUserRepository(user.getNameUser());
        List<String> startList = HelperMethods.generateStringList(path);
        return startList;
    }

    public ConnectionsController getConnectionsController() {
        return connectionsController;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Storage getStorage() {
        return storage;
    }

}
