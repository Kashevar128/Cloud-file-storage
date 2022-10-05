package org.vinogradov.myclient.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertWindowsClass {

    public static void showIncorrectPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат пароля." +
                " Допускаются не менее 1 латинской буквы в нижним регистре, верхнем регистре," +
                " цифры, количество символов от 8 до 20.", ButtonType.OK);
        alert.setHeaderText("Попробуйте еще раз");
        alert.showAndWait();
    }

    public static void showIncorrectUserNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат имени пользователя." +
                " Допускаются латинский буквы в верхнем и нижнем регистрах, цифры, кол-во" +
                " символов от 1 до 30.", ButtonType.OK);
        alert.setHeaderText("Попробуйте еще раз");
        alert.showAndWait();
    }

    public static void showRegComplete() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Регистрация прошла успешно.", ButtonType.OK);
        alert.setHeaderText("Регистрация");
        alert.showAndWait();
    }

    public static void showRegFalse() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Такой пользователь уже существует.", ButtonType.OK);
        alert.setHeaderText("Ошибка регистрации");
        alert.showAndWait();
    }

    public static void showAuthComplete() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Аутентификация прошла успешно.", ButtonType.OK);
        alert.setHeaderText("Аутентификация");
        alert.showAndWait();
    }

    public static void showAuthFalse() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный пользователь или пароль.", ButtonType.OK);
        alert.setHeaderText("Ошибка аутентификации");
        alert.showAndWait();
    }


}

