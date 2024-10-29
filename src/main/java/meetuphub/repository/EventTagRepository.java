package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;
import meetuphub.models.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface EventTagRepository {
    String INSERT_EVENT_CATEGORY = "INSERT INTO event_tag VALUES(?, ?)";
    String SELECT_TAGS_BY_EVENT_ID = "SELECT t.* FROM tag t " +
            "JOIN event_tag et ON t.id = et.tag_id " +
            "WHERE et.event_id = ?";

    static void addTagToEvent(int eventId, int tagId) {
        try (Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT_CATEGORY)) {
            preparedStatement.setInt(1, eventId);
            preparedStatement.setInt(2, tagId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }

    static List<Tag> getTagsByEventId(int eventId) {
        List<Tag> tags = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAGS_BY_EVENT_ID)) {
            preparedStatement.setInt(1, eventId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setId(resultSet.getInt("id"));
                tag.setName(resultSet.getString("name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
        return tags;
    }
}
