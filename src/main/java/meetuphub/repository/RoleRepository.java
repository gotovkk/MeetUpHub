package meetuphub.repository;

import meetuphub.DatabaseConnection;
import meetuphub.exception.DatabaseException;
import meetuphub.exception.UserNotFoundException;
import meetuphub.exception.UserUpdateException;
import meetuphub.model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface RoleRepository {
    String INSERT_ROLE = "INSERT INTO role (name) VALUES (?);";
    String DELETE_ROLE = "DELETE FROM role WHERE id = ?";

    static List<Role> getRoleData(String query, Object... params) {
        List<Role> roles = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");


                roles.add(new Role(id, name));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при получении данных пользователя.");
        }

        return roles;
    }

    static List<Role> saveRoleData(Role role) {
        List<Role> roles = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ROLE)) {
            preparedStatement.setString(1, role.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка при сохранени данных о роли.");
        }
        return roles;
    }

    static void updateRoleData(Role role) {
        // Подумать может что-то еще кроме StringBuilder
        StringBuilder query = new StringBuilder("UPDATE role SET ");
        List<Object> params = new ArrayList<>();

        if (role.getName() != null) {
            query.append("name = ?,");
            params.add(role.getName());
        }

        if (params.isEmpty()) {
            throw new IllegalStateException("Не указаны параметры для обновления.");
        }

        query.setLength(query.length() - 2);
        query.append(" WHERE id = ?");
        params.add(role.getId());

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query.toString())) {

            for (int i = 0; i < params.size(); i++) {
                preparedStatement.setObject(i + 1, params.get(i));
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserUpdateException("Ошибка обновлении данных.");
        }
    }

    static List<Role> deleteRoleData(int roleId) {
        List<Role> roles = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ROLE)) {

            preparedStatement.setInt(1, roleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new UserNotFoundException("Роль: " + roleId + " не найден.");
        }
        return roles;
    }
}
