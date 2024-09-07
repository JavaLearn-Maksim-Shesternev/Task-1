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
        String query = """
                CREATE TABLE IF NOT EXISTS users (
                id BIGSERIAL PRIMARY KEY,
                name VARCHAR(128),
                last_name VARCHAR(128),
                age SMALLINT
                )
                """;

        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(query);
            System.out.println("Created users table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = """
                DROP TABLE IF EXISTS users
                """;

        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(query);
            System.out.println("Dropped users table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = """
                INSERT INTO users (name , last_name , age) values (?, ?, ?)
                """;

        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User " + name + " saved in database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = """
                DELETE FROM users WHERE id = ?
                """;

        try (Connection connection = Util.open();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("User with id " + id + " removed from database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = """
                SELECT * FROM users
                """;

        try (Connection connection = Util.open();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("last_name");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String query = """
                TRUNCATE TABLE users
                """;

        try (Connection connection = Util.open();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            System.out.println("All users removed from the table");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
