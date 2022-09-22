module client {
    requires javafx.fxml;
    requires javafx.controls;
    requires dto;
    requires support;
    requires io.netty.transport;
    requires io.netty.codec;

    exports org.vinogradov.myclient.controllers;
    exports org.vinogradov.myclient.GUI;

}
