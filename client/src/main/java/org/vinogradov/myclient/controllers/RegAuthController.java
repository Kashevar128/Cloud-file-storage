package org.vinogradov.myclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.myclient.GUI.AlertWindowsClass;
import org.vinogradov.myclient.clientService.ClientLogic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegAuthController {

    private ClientLogic clientLogic;

    private final Path pathStart = Paths.get("client/src/main/resources/org.vinogradov.txt/start.txt");

    private List<String> authFields;

    private final BiConsumer<String, String> regConsumer = (name, pass) -> clientLogic.createRegClientRequest(name, pass);

    private final BiConsumer<String, String> authConsumer = (name, pass) -> clientLogic.createAuthClientRequest(name, pass);

    @FXML
    public TextField userField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void regUser() {
        getStarted(regConsumer);
    }

    @FXML
    public void authUser() {
        getStarted(authConsumer);
    }

    @FXML
    public void clearFields(ActionEvent actionEvent) {
        userField.clear();
        passwordField.clear();
        if (Files.exists(pathStart)) {
            try {
                Files.delete(pathStart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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

    public void fillInTheFields() {
        if (Files.exists(pathStart)) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(pathStart.toFile()));
                authFields = (List<String>) objectInputStream.readObject();
                userField.setText(authFields.get(0));
                passwordField.setText(authFields.get(1));
                objectInputStream.close();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveFields(String name, String path) {
        try {
            if (Files.exists(pathStart)) Files.delete(pathStart);
            Files.createFile(pathStart);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(pathStart.toFile()));
            authFields = new ArrayList<>();
            authFields.add(name);
            authFields.add(path);
            objectOutputStream.writeObject(authFields);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStarted(BiConsumer<String, String> startConsumer) {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        if (filter(name, pass)) {
            startConsumer.accept(name, pass);
            saveFields(name, pass);
        }
    }


}
