package org.vinogradov.myclient.GUI;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

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
        Alert alert = new Alert(Alert.AlertType.ERROR, "Неверный пользователь " +
                "или пароль.", ButtonType.OK);
        alert.setHeaderText("Ошибка аутентификации");
        alert.showAndWait();
    }

    public static void showDelFileError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректное удаление файла.", ButtonType.OK);
        alert.setHeaderText("Ошибка удаления файла");
        alert.showAndWait();
    }

    public static void showUpdateListError() {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Некорректное обновление " +
                "списка файлов.", ButtonType.OK);
        alert.setHeaderText("Ошибка обновления листа файлов");
        alert.showAndWait();
    }

    public static void showConnectionLimit() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Время сессии вышло.", ButtonType.OK);
        alert.setHeaderText("!!!");
        alert.showAndWait();
    }

    public static void showSelectFileAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите файл, " +
                "который хотите перенести или удалить.", ButtonType.OK);
        alert.setHeaderText("Вы не выбрали файл");
        alert.showAndWait();
    }

    public static void showSelectTableAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Выберите панель " +
                "сервера или панель клиента для " +
                "создания новой папки.", ButtonType.OK);
        alert.setHeaderText("Вы не выбрали панель");
        alert.showAndWait();
    }

    public static void showLengthFolderNameAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "В названии папки должно " +
                "быть не больше 50 сиволов", ButtonType.OK);
        alert.setHeaderText("Слишком длинное имя папки");
        alert.showAndWait();
    }

    public static void showInterruptSendFileAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Передача файла прервана, " +
                "переданная часть файла будет автоматичиски удалена", ButtonType.OK);
        alert.setHeaderText("Передача прервана!");
        alert.showAndWait();
    }

    public static void showSizeCloudAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Не хватает места в хранилище", ButtonType.OK);
        alert.setHeaderText("Передача прервана!");
        alert.showAndWait();
    }

    public static void showNotCreateNextDirectoryAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Вы превысили " +
                "допустимую вложенность директорий", ButtonType.OK);
        alert.setHeaderText("Папка не создана!");
        alert.showAndWait();
    }

    public static void showTheUserIsAlreadyLoggedInAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Пользователь с вашими данными уже online", ButtonType.OK);
        alert.setHeaderText("Вы уже вошли");
        alert.showAndWait();
    }

    public static boolean showOnTheClientFileExistingConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "На клиенте такой файл существует. Хотите его перезаписать?");
        alert.setHeaderText("Перезапись файла на клиенте");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static boolean showOnTheServerFileExistingConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "На сервере такой файл существует. Хотите его перезаписать?");
        alert.setHeaderText("Перезапись файла на сервере");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public static void showLossOfConnectionAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Соединение с сервером разорвано", ButtonType.OK);
        alert.setHeaderText("Потеря соединения!");
        alert.showAndWait();
    }

    public static void showFallsConnectAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Отсутствует соединение с сервером, программа будет закрыта", ButtonType.OK);
        alert.setHeaderText("Нет соединеня!");
        alert.showAndWait();
    }

}

