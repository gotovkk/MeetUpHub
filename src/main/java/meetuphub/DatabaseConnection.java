package meetuphub;

import meetuphub.exception.DatabaseException;
import meetuphub.exception.FileException;

import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private DatabaseConnection() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        String dbURL;
        String dbUsername;
        String dbPassword;

        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new FileException("Ошибка при попытке найти файл.");
            }
            properties.load(inputStream);
            dbURL = properties.getProperty("db.host");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new FileException("Ошибка чтения файла.");
        }

        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection=  DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseException("Ошибка подключения к базе данных.");
        }

        return connection;
    }

}
