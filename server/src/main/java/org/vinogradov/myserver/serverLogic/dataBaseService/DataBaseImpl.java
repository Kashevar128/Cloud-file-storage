package org.vinogradov.myserver.serverLogic.dataBaseService;

import org.apache.commons.codec.digest.DigestUtils;
import org.vinogradov.common.commonClasses.Constants;
import org.vinogradov.common.commonClasses.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseImpl implements DataBase {

    private Connection connection;

    //language=SQLite
    private final String queryCreateTable = "CREATE TABLE IF NOT EXISTS Users(Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "User TEXT UNIQUE, Password TEXT, Access INTEGER)";

    //language=SQLite
    private final String queryGetAllUsers = "SELECT * FROM Users";

    //language=SQLite
    private final String queryNewUser = "INSERT INTO Users(User, Password, Access) VALUES (?, ?, ?)";

    //language=SQLite
    private final String queryGetUserForName = "SELECT * FROM Users WHERE User = ?";

    //language=SQLite
    private final String queryDeleteUser = "DELETE FROM Users WHERE User = ?";

    //language=SQLite
    private final String queryAccess = "UPDATE Users SET Access = ? WHERE User = ?";


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
                statement.setInt(3, Constants.ACCESS_TRUE);
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

    @Override
    public List<List<String>> showAllUser() {
        List<List<String>> arrayUser = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(queryGetAllUsers);
            while (rs.next()) {
                List<String> paramList = new ArrayList<>();
                paramList.add(rs.getString(1));
                paramList.add(rs.getString(2));
                paramList.add(rs.getString(3));
                paramList.add(rs.getString(4));
                arrayUser.add(paramList);
            }
            return arrayUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> showUser(String name) {
        List<String> array = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(queryGetUserForName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            array.add(rs.getString(1));
            array.add(rs.getString(2));
            array.add(rs.getString(3));
            array.add(rs.getString(4));
            return array;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setAccess(String name, int param) {
        try (PreparedStatement statement = connection.prepareStatement(queryAccess)) {
            statement.setInt(1, param);
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean getAccess(String name) {
        try (PreparedStatement statement = connection.prepareStatement(queryGetUserForName)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            return rs.getInt(4) == Constants.ACCESS_TRUE;
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
