package meetuphub.repository;

import meetuphub.DatabaseConnection;
import meetuphub.exception.DatabaseException;

import java.sql.*;
import java.time.LocalDateTime;

public interface EventUserRepository {
    String INSERT_EVENT_USER = "INSERT INTO event_user VALUES(?, ?, ?, ?)";

    static void addUserToEvent(int eventId, int userId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_USER)) {
            preparedStatement.setInt(1, userId)
            preparedStatement.setInt(2, eventId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Указываем текущее время
            preparedStatement.setString(4, "registered");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
       throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
    public static boolean isUserRegisteredForEvent(int userId, int eventId) {
        String query = "SELECT COUNT(*) FROM event_user WHERE user_id = ? AND event_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, eventId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
