package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface EventCategoryRepository {
    String INSERT_EVENT_CATEGORY = "INSERT INTO event_category VALUES(?, ?)";

    static void addUserToEvent(int eventId, int categoryId) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_CATEGORY)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
