package org.vinogradov.myclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.clientService.ClientLogic;
import org.vinogradov.mysupport.HelperMethods;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegAuthController {

    ClientLogic clientLogic;

    @FXML
    public TextField userField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void regUser() {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        if (filter(name, pass)) {
            clientLogic.createRegClientRequest(name, pass);
        }
    }

    @FXML
    public void authUser() {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        if (filter(name, pass)) {
            clientLogic.createAuthClientRequest(name, pass);
        }
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        userField.clear();
        passwordField.clear();
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

    public ClientLogic getClientLogic() {
        return clientLogic;
    }

    public void setClientLogic(ClientLogic clientLogic) {
        this.clientLogic = clientLogic;
    }
}
