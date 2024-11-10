package meetuphub.repository;

import meetuphub.DatabaseConnection;
import meetuphub.exception.DatabaseException;
import meetuphub.exception.UserNotFoundException;
import meetuphub.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository {
    String INSERT_CATEGORY = "INSERT INTO category (name, description) VALUES (?, ?);";
    String DELETE_CATEGORY = "DELETE FROM category WHERE id = ?";

    static List<Category> getCategoryData(String query, Object... params) {
        List<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");

                categories.add(new Category(id, name, description));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных о категории.");
        }

        return categories;
    }

    static List<Category> saveCategoryData(Category category) {
        String sql = new StringBuilder().append("INSERT INTO category (name, description)").append("VALUES (?, ?) RETURNING id").toString();

        List<Category> categories = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    category.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Не удалось получить ID события.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени данных о категории.");
        }
        return categories;
    }

    static List<Category> deleteCategoryData(int categoryId) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY)) {

            preparedStatement.setInt(1, categoryId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Категория с id: " + categoryId + " не найден.");
        }
        return categories;
    }
}
