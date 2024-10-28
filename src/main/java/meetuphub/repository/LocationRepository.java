package meetuphub.repository;

import meetuphub.DBUtils;
import meetuphub.exceptions.DatabaseException;

import meetuphub.exceptions.UserNotFoundException;
import meetuphub.exceptions.UserUpdateException;
import meetuphub.models.Event;
import meetuphub.models.Location;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface LocationRepository {
    String INSERT_LOCATION = "INSERT INTO location (name, address, description) VALUES (?, ?, ?);";
    String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";

    static List<Location> getLocationData(String query) {
        List<Location> locations = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                String description = rs.getString("description");

                locations.add(new Location(id, name, address, description));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных пользователя.");
        }

        return locations;
    }

    static List<Location> saveLocationData(Location location) {
        List<Location> locations = new ArrayList<>();

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_LOCATION)) {
            preparedStatement.setString(1, location.getName());
            preparedStatement.setString(2, location.getAddress());
            preparedStatement.setString(3, location.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени данных о локации.");
        }
        return locations;
    }

    static void updateLocationData(Location location) {
        // Подумать может что-то еще кроме StringBuilder
        StringBuilder query = new StringBuilder("UPDATE location SET ");
        List<Object> params = new ArrayList<>();

        if (location.getName() != null) {
            query.append("name = ?,");
            params.add(location.getName());
        }
        if (location.getAddress() != null) {
            query.append("description = ?,");
            params.add(location.getName());
        }
        if (location.getDescription() != null) {
            query.append("status = ?,");
            params.add(location.getName());
        }

        if (params.isEmpty()) {
            throw new IllegalStateException("Не указаны параметры для обновления.");
        }

        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        params.add(location.getId());

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

    static List<Location> deleteLocationData(int locationId) {
        List<Location> locations = new ArrayList<>();
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LOCATION)) {

            preparedStatement.setInt(1, locationId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Локация с id: " + locationId + " не найден.");
        }
        return locations;
    }
}
