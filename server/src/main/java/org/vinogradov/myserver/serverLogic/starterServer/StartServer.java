package org.vinogradov.myserver.serverLogic.starterServer;

import org.vinogradov.myserver.serverLogic.consoleService.ServerConsole;
import org.vinogradov.myserver.serverLogic.serverService.NettyServer;

public class StartServer {
    public static void main(String[] args) {
        try {
            new NettyServer();
        } catch (InterruptedException e) {
            throw new RuntimeException("Проблемы при запуске сервера");
        }
    }
}
