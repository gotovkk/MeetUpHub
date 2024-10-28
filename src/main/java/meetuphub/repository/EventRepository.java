package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.models.Event;
import meetuphub.exceptions.DatabaseException;
import meetuphub.exceptions.UserNotFoundException;
import meetuphub.exceptions.UserUpdateException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface EventRepository {
    String INSERT_EVENT = "INSERT INTO event (name, description, status, start_time, end_time, location_id, organizer_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String DELETE_EVENT = "DELETE FROM event WHERE id = ?";
    String INSERT_EVENT_CATEGORY = "INSERT INTO event_categories (event_id, category_id) VALUES (?, ?)";
    String INSERT_EVENT_PARTICIPANT = "INSERT INTO event_participants (event_id, participant_id) VALUES (?, ?)";
    String INSERT_EVENT_TAG = "INSERT INTO event_tags (event_id, tag_id) VALUES (?, ?)";


    static List<Event> getEventData(String query) {
        List<Event> events = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                String status = rs.getString(4);
                LocalDateTime startTime = rs.getTimestamp(5).toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp(6).toLocalDateTime();
                rs.getTimestamp("created_at");
                LocalDateTime createdAt = null;
                int locationId = rs.getInt(8);
                int organizerId = rs.getInt(9);

                events.add(new Event(id, name, description, status, startTime, endTime, createdAt, locationId, organizerId));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных пользователя");
        }

        return events;
    }

    static List<Event> saveEventData(Event event) {
        List<Event> events = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();

             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EVENT)) {
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getDescription());
            preparedStatement.setString(3, event.getStatus());
            preparedStatement.setString(4, event.getStartTime().toString());
            preparedStatement.setString(5, event.getEndTime().toString());

            // Тут потом привязку айди от юзера который сейчас
            Integer organizerId = event.getOrganizerId();
            preparedStatement.setInt(6, organizerId);

            Integer locationId = event.getLocationId();
            preparedStatement.setInt(7, locationId);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени события пользователя.");
        }
        return events;
    }


    static void updateEventData(Event event) {
        // Подумать может что-то еще кроме stringbuilder
        StringBuilder query = new StringBuilder("UPDATE event SET ");
        List<Object> params = new ArrayList<>();

        if (event.getName() != null) {
            query.append("name = ?,");
            params.add(event.getName());
        }
        if (event.getDescription() != null) {
            query.append("description = ?,");
            params.add(event.getName());
        }
        if (event.getStatus() != null) {
            query.append("status = ?,");
            params.add(event.getName());
        }
        if (event.getStartTime() != null) {
            query.append("start_time = ?,");
            params.add(event.getName());
        }
        if (event.getEndTime() != null) {
            query.append("end_time = ?,");
            params.add(event.getName());
        }
        if (event.getLocationId() != null) {
            query.append("location_id = ?,");
            params.add(event.getName());
        }
        if (event.getOrganizerId() != null) {
            query.append("organizer_id = ?,");
            params.add(event.getName());
        }

        if (params.isEmpty()) {
            throw new IllegalStateException("Не указаны параметры для обновления.");
        }

        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        params.add(event.getId());

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

    static List<Event> deleteEventData(int eventId) {
        List<Event> events = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_EVENT)) {

            preparedStatement.setInt(1, eventId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Пользователь с id: " + eventId + " не найден.");
        }
        return events;
    }

}
