package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface EventUserRepository {
    String INSERT_EVENT_USER = "insert into \"event_user\" values(?, ?, ?, ?)";

    static void addUserToEvent(int eventId, int userId) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_USER)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
