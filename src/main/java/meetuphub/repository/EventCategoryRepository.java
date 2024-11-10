package meetuphub.repository;

import meetuphub.DatabaseConnection;
import meetuphub.exception.DatabaseException;
import meetuphub.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EventCategoryRepository {
    String INSERT_EVENT_CATEGORY = "INSERT INTO event_category VALUES(?, ?)";
    String SELECT_CATEGORIES_BY_EVENT_ID = "SELECT c.* FROM category c " +
            "JOIN event_category ec ON c.id = ec.category_id " +
            "WHERE ec.event_id = ?";


    static void addEventToCategory(int eventId, int categoryId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_CATEGORY)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
    static List<Category> getCategoriesByEventId(int eventId) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORIES_BY_EVENT_ID)) {
            preparedStatement.setInt(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Category category = new Category();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
        return categories;
    }
}
