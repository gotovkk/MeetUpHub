package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.models.Event;
import meetuphub.models.User;
import meetuphub.exceptions.DatabaseException;
import meetuphub.exceptions.UserAlreadyExistException;
import meetuphub.exceptions.UserNotFoundException;
import meetuphub.exceptions.UserUpdateException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface UserRepository {
    String INSERT_USER = "INSERT INTO \"user\" (name, email, password_hash) VALUES (?, ?, ?);";
    String UPDATE_USER = "UPDATE \"user\" SET name = ? WHERE id = ?";
    String DELETE_USER = "DELETE FROM \"user\" WHERE id = ?";

    static List<User> getUserData(String query, Object... params) {
        List<User> users = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password_hash");
                rs.getTimestamp("created_at");
                LocalDateTime createdAt = null;

                users.add(new User(id, name, email, passwordHash, createdAt));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных пользователя.");
        }

        return users;
    }

    static List<User> saveUserData(User user) {
        List<User> users = new ArrayList<>();

        if (!getUserData("SELECT * FROM \"user\" WHERE email = '" + user.getEmail() + "'").isEmpty()) {
            throw new UserAlreadyExistException("Пользователь с таким email уже существует: " + user.getEmail());

        }

        try (Connection connection = DBUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPasswordHash());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени данных пользователя.");
        }
        return users;
    }

    static List<User> updateUserData(int userId, String username) {
        List<User> users = new ArrayList<>();

        if (getUserData("SELECT * FROM \"user\" WHERE id = ?", userId).isEmpty()) {
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден.");
        }

        try (Connection connection = DBUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER)) {

            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserUpdateException("Ошибка обновлении данных.");
        }
        return users;
    }

    static List<User> deleteUserData(int userId) {
        List<User> users = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Пользователь с id: " + userId + " не найден.");
        }
        return users;
    }

//    static List<Event> getEventForUser(int userId) {
//        return EventUserRepository.getEventByUserId(userId);
//    }

}
