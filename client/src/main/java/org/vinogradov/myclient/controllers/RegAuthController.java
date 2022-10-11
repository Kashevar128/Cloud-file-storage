package org.vinogradov.myclient.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.clientLogic.NettyClient;
import org.vinogradov.mydto.User;
import org.vinogradov.mydto.requests.AuthClientRequest;
import org.vinogradov.mydto.requests.RegClientRequest;
import org.vinogradov.mysupport.HelperMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegAuthController {

    NettyClient nettyClient;

    @FXML
    public TextField userField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void regUser() {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        if (filter(name, pass)) {
            nettyClient.sendMessage(new RegClientRequest(new User(name, pass)));
        }
    }

    @FXML
    public void authUser() {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        if (filter(name, pass)) {
            nettyClient.sendMessage(new AuthClientRequest(new User(name, pass)));
        }
    }

    public void exit() {

    }

    private boolean filter(String userField, String passwordField) {
        if (userField.isBlank() || passwordField.isBlank()) {
            return false;
        }

        Pattern patternNameUser = Pattern.compile("^[a-zA-Z0-9_.]{1,30}$");
        Pattern patternPassword = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
        Matcher matcherNameUser = patternNameUser.matcher(userField);
        Matcher matcherPassword = patternPassword.matcher(passwordField);

        if (!matcherNameUser.matches()) {
            AlertWindowsClass.showIncorrectUserNameAlert();
            return false;
        }

        if (!matcherPassword.matches()) {
            AlertWindowsClass.showIncorrectPasswordAlert();
            return false;
        }

        return true;
    }

    public void setNettyClient(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }
}
