package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.mysupport.HelperMethods;

import java.sql.*;

public class DataBaseImpl implements DataBase {

    private Connection connection;

    private String queryCreateTable = "CREATE TABLE IF NOT EXISTS Users(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "User TEXT UNIQUE, Password TEXT)";

    private String queryGetAllUsers = "SELECT * FROM Users";

    private String queryNewUser = "INSERT INTO Users(User, Password) VALUES (?, ?)";

    private String queryGetUserForName = "SELECT * FROM Users WHERE User = ?";

    public DataBaseImpl() throws Exception {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
        System.out.println("База данных подключена");
        createTable();
    }

    private void createTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(queryCreateTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized boolean createUser(String name, String password) {
        if (!findUser(name)) {
            String passwordMd5 = DigestUtils.md5Hex(password);
            try (PreparedStatement statement = connection.prepareStatement(queryNewUser)) {
                statement.setString(1, name);
                statement.setString(2, passwordMd5);
                statement.executeUpdate();
                return true;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean findUser(String name) {
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(queryGetAllUsers);
            while (rs.next()) {
                if (rs.getString("User").equals(name)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean auth(String name, String password) {
        if (!findUser(name)) return false;
        String passwordMd5 = DigestUtils.md5Hex(password);
        try (PreparedStatement statement = connection.prepareStatement(queryGetUserForName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            String passTable = rs.getString("Password");
            if (!passTable.equals(passwordMd5)) return false;
            else return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteUser(String name) {

    }

    @Override
    public void startDataBase() {

    }

    @Override
    public void stopDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
