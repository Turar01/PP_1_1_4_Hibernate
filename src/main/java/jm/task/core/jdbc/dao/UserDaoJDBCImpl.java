package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR(255)," +
                "lastName VARCHAR(255)," +
                "age TINYINT" +
                ")";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createTableSQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS user";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(dropTableSQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String addUserSQL = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(addUserSQL);
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM user WHERE id = ?";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteUserSQL);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String getAllUsersSQL = "SELECT * FROM user";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(getAllUsersSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }


    public void cleanUsersTable() {
        String clearTableSQL = "DELETE FROM user";

        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(clearTableSQL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
