package org.vinogradov.myserver.serverLogic;

import io.netty.channel.Channel;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.OperationBanResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.mysupport.HelperMethods;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ServerHandlerLogicImpl implements ServerHandlerLogic {

    private final DataBase dataBase;

    private final Storage storage;

    private final UsersList usersListChannels;

    public ServerHandlerLogicImpl() {
        try {
            this.dataBase = new DataBaseImpl();
            this.storage = new Storage();
            this.usersListChannels = new UsersList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendRegServerResponse(RegClientRequest regClient) {
        User user = regClient.getUser();
        boolean regComplete = dataBase.createUser(user.getNameUser(), user.getPassword());
        if (regComplete) {
            Path path = storage.createUserRepository(user.getNameUser());
            List<String> startList = HelperMethods.generateStringList(path);
            sendMessage(user, new RegServerResponse(true, user, startList));
            return;
        }
        sendMessage(user, new RegServerResponse(false));
    }

    @Override
    public void sendAuthServerResponse(AuthClientRequest authClient) {
        User user = authClient.getUser();
        boolean authComplete = dataBase.auth(user.getNameUser(), user.getPassword());
        if (authComplete) {
            Path path = storage.createUserRepository(user.getNameUser());
            List<String> startList = HelperMethods.generateStringList(path);
            sendMessage(user, new AuthServerResponse(true, user, startList));
            return;
        }
        sendMessage(user, new AuthServerResponse(false));
    }

    @Override
    public void sendListResponse(GetListRequest listRequest) {
        security(listRequest);
        User user = listRequest.getUser();
        Path path = Paths.get(listRequest.getPath());
        List<String> newList = HelperMethods.generateStringList(path);
        sendMessage(user, new GetListResponse(newList));
    }

    private void security(BasicQuery basicQuery) {
        User user = basicQuery.getUser();
        boolean auth = dataBase.auth(user.getNameUser(), user.getPassword());
        if (auth) return;
        sendMessage(user, new OperationBanResponse());
    }

    private void sendMessage(User user, BasicQuery basicQuery) {
        Channel channel = usersListChannels.getUserChannel(user.getNameUser());
        channel.writeAndFlush(basicQuery);
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Storage getStorage() {
        return storage;
    }

    public UsersList getUsersListChannels() {
        return usersListChannels;
    }

}
