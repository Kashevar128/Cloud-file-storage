package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.common.commonClasses.User;

import java.sql.*;

public class DataBaseImpl implements DataBase {

    private Connection connection;

    private final String queryCreateTable = "CREATE TABLE IF NOT EXISTS Users(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "User TEXT UNIQUE, Password TEXT)";

    private final String queryGetAllUsers = "SELECT * FROM Users";

    private final String queryNewUser = "INSERT INTO Users(User, Password) VALUES (?, ?)";

    private final String queryGetUserForName = "SELECT * FROM Users WHERE User = ?";

    private final String queryDeleteUser = "DELETE FROM Users WHERE User = ?";

    public DataBaseImpl() throws Exception {
        Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection("jdbc:sqlite:users.sqlite");
        System.out.println("The database is connected");
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
    public synchronized boolean createUser(User user) {
        String name = user.getNameUser();
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword());
        if (!findUser(name)) {
            try (PreparedStatement statement = connection.prepareStatement(queryNewUser)) {
                statement.setString(1, name);
                statement.setString(2, encryptedPassword);
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
    public boolean auth(User user) {
        String name = user.getNameUser();
        String encryptPassword = DigestUtils.md5Hex(user.getPassword());
        if (!findUser(name)) return false;
        try (PreparedStatement statement = connection.prepareStatement(queryGetUserForName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            String passTable = rs.getString("Password");
            if (!passTable.equals(encryptPassword)) return false;
            else return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteUser(String name) {
        if (!findUser(name)) return false;
        try (PreparedStatement statement = connection.prepareStatement(queryDeleteUser)) {
            statement.setString(1, name);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void closeDataBase() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
