package meetuphub.repository;

import meetuphub.DatabaseConnection;
import meetuphub.exception.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface UserRoleRepository {
    String INSERT_USER_ROLE = "INSERT INTO user_role VALUES(?, ?)";

    static void addRoleToUser(int userId, int roleId) {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_ROLE)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, roleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных");
        }
    }
}
