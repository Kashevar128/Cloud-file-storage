package org.vinogradov.myclient.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertWindowsClass {

    public static void showIncorrectPasswordAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат пароля." +
                " Допускаются не менее 1 латинской буквы в нижним регистре, верхнем регистре," +
                " цифры, количество символов от 8 до 20", ButtonType.OK);
        alert.setHeaderText("Попробуйте еще раз");
        alert.showAndWait();
    }

    public static void showIncorrectUserNameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный формат имени пользователя." +
                " Допускаются латинский буквы в верхнем и нижнем регистрах, цифры, кол-во" +
                " символов от 1 до 30 ", ButtonType.OK);
        alert.setHeaderText("Попробуйте еще раз");
        alert.showAndWait();
    }
}
