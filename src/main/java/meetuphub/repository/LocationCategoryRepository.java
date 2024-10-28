package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface LocationCategoryRepository {
    String INSERT_LOCATION_CATEGORY = "INSERT INTO location_category VALUES(?, ?)";

    static void addEventToLocation(int locationId, int categoryId) {
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCATION_CATEGORY)) {
            preparedStatement.setInt(1, locationId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
