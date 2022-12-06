module client {
    requires javafx.fxml;
    requires javafx.controls;
    requires common;
    requires io.netty.transport;
    requires io.netty.codec;

    exports org.vinogradov.myclient.controllers;
    exports org.vinogradov.myclient.GUI;
    exports org.vinogradov.myclient.starterClient;

}
