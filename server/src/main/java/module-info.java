module server {
    requires io.netty.transport;
    requires io.netty.codec;
    requires java.sql;
    requires org.apache.commons.codec;
    requires java.desktop;
    requires common;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;

    exports org.vinogradov.myserver.serverLogic.consoleService.controllers;
}
