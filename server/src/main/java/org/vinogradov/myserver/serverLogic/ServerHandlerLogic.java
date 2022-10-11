package org.vinogradov.myserver.serverLogic;

import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mydto.responses.AuthServerResponse;
import org.vinogradov.mydto.responses.RegServerResponse;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBase;
import org.vinogradov.myserver.serverLogic.dataBaseService.DataBaseImpl;
import org.vinogradov.mysupport.HelperMethods;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ServerHandlerLogic {

    static private DataBase dataBase;

    static private Storage storage;

    static private ConcurrentMap <ChannelHandlerContext, User> userMap;


    static {
        try {
            dataBase = new DataBaseImpl();
            storage = new Storage();
            userMap = new ConcurrentHashMap<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static RegServerResponse getRegServerResponse (RegClientRequest regClient) {
        User user = regClient.getUser();
        String passCryptMd5 = DigestUtils.md5Hex(user.getPassword());
        boolean regComplete = dataBase.createUser(user.getNameUser(), passCryptMd5);
        if (regComplete) {
            List<String> startList = createStartMessage(user);
            return new RegServerResponse(true, user, startList);
        }
        return new RegServerResponse(false);
    }

    static AuthServerResponse getAuthServerResponse (AuthClientRequest authClient) {
        User user = authClient.getUser();
        String passCryptMd5 = DigestUtils.md5Hex(authClient.getUser().getPassword());
        boolean authComplete = dataBase.auth(authClient.getUser().getNameUser(), passCryptMd5);
        if (authComplete) {
            List<String> startList = createStartMessage(user);
            return new AuthServerResponse(true, user, startList);
        }
        return new AuthServerResponse(false);
    }


    private static List<String> createStartMessage(User user) {
        Path path = storage.createUserRepository(user.getNameUser());
        List<String> startList = HelperMethods.generateStringList(path);
        return startList;
    }
}
