package org.vinogradov.myserver.serverLogic;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.mydto.BasicQuery;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.GetListRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.GetListResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.mysupport.HelperMethods;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServerHandlerLogic {

    static private DataBase dataBase;

    static private Storage storage;

    static private ConcurrentMap<String, ChannelHandlerContext> usersMap;


    static {
        try {
            dataBase = new DataBaseImpl();
            storage = new Storage();
            usersMap = new ConcurrentHashMap<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static RegServerResponse getRegServerResponse(RegClientRequest regClient, ChannelHandlerContext context) {
        User user = regClient.getUser();
        boolean regComplete = dataBase.createUser(user.getNameUser(), user.getPassword());
        if (regComplete) {
            List<String> startList = createStartMessage(user, context);
            return new RegServerResponse(true, user, startList);
        }
        return new RegServerResponse(false);
    }

    static AuthServerResponse getAuthServerResponse(AuthClientRequest authClient, ChannelHandlerContext context) {
        User user = authClient.getUser();
        boolean authComplete = dataBase.auth(user.getNameUser(), user.getPassword());
        if (authComplete) {
            List<String> startList = createStartMessage(user, context);
            return new AuthServerResponse(true, user, startList);
        }
        return new AuthServerResponse(false);
    }

    static GetListResponse getListResponse(GetListRequest listRequest) {
        Path path = Paths.get(listRequest.getPath());
        List<String> newList = HelperMethods.generateStringList(path);
        return new GetListResponse(newList);
    }

    static boolean security(BasicQuery basicQuery) {
        User user = basicQuery.getUser();
        boolean auth = dataBase.auth(user.getNameUser(), user.getPassword());
        if (auth) {
            return true;
        }
        return false;
    }




    private static List<String> createStartMessage(User user, ChannelHandlerContext context) {
        Path path = storage.createUserRepository(user.getNameUser());
        List<String> startList = HelperMethods.generateStringList(path);
        usersMap.put(user.getNameUser(), context);
        return startList;
    }
}
