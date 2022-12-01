package org.vinogradov.myclient.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.vinogradov.common.commonClasses.HelperMethods;
import org.vinogradov.common.commonClasses.StatusUser;
import org.vinogradov.common.commonClasses.User;
import org.vinogradov.common.requests.PatternMatchingRequest;
import org.vinogradov.myclient.clientService.ClientLogic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RegAuthController {

    private ClientLogic clientLogic;

    private final Path pathStart = Paths.get("client/src/main/resources/org.vinogradov.txt/start.txt");

    private List<String> authFields;

    @FXML
    public TextField userField;

    @FXML
    public PasswordField passwordField;

    @FXML
    public void authUser(ActionEvent actionEvent) {
        getStarted(StatusUser.AUTH);
    }

    @FXML
    public void regUser(ActionEvent actionEvent) {
        getStarted(StatusUser.REG);
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

    private boolean fieldsBlank(String userField, String passwordField) {
        return userField.isBlank() && passwordField.isBlank();
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

    private void getStarted(StatusUser statusUser) {
        String name = HelperMethods.delSpace(userField.getText());
        String pass = HelperMethods.delSpace(passwordField.getText());
        User user = new User(name, pass);
        if (fieldsBlank(name, pass)) return;
        saveFields(name, pass);
        clientLogic.sendMessage(new PatternMatchingRequest(user, statusUser));
    }
}
