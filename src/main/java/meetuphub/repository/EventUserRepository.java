package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface EventUserRepository {
    String INSERT_EVENT_USER = "INSERT INTO event_user VALUES(?, ?, ?, ?)";

    static void addUserToEvent(int eventId, int userId) {
        try (Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_USER)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, eventId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Указываем текущее время
            preparedStatement.setString(4, "registered");

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
       throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
