package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface EventTagRepository {
    String INSERT_EVENT_CATEGORY = "INSERT INTO event_tag VALUES(?, ?)";

    static void addUserToEvent(int eventId, int tagId) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_CATEGORY)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, tagId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
