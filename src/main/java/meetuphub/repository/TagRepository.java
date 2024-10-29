package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;
import meetuphub.exceptions.UserNotFoundException;
import meetuphub.exceptions.UserUpdateException;
import meetuphub.models.Tag;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface TagRepository {
    String DELETE_TAG = "DELETE FROM tag WHERE id = ?";

    static List<Tag> getTagData(String query, Object... params) {
        List<Tag> tags = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                tags.add(new Tag(id, name));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных о теге.");
        }

        return tags;
    }

    static List<Tag> saveTagData(Tag tag) {
        String sql = new StringBuilder().append("INSERT INTO tag (name)").append("VALUES (?) RETURNING id").toString();

        List<Tag> tags = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, tag.getName());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tag.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Не удалось получить ID события.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени данных о теге.");
        }
        return tags;
    }

    static void updateTagData(Tag tag) {
        // Подумать может что-то еще кроме StringBuilder
        StringBuilder query = new StringBuilder("UPDATE tag SET ");
        List<Object> params = new ArrayList<>();

        if (tag.getName() != null) {
            query.append("name = ?,");
            params.add(tag.getName());
        }


        if (params.isEmpty()) {
            throw new IllegalStateException("Не указаны параметры для обновления.");
        }

        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        params.add(tag.getId());

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserUpdateException("Ошибка обновлении данных.");
        }
    }

    static List<Tag> deleteTagData(int tagId) {
        List<Tag> roles = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_TAG)) {

            preparedStatement.setInt(1, tagId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Тег с id: " + tagId + " не найден.");
        }
        return roles;
    }
}
