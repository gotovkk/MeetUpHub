package meetuphub;

import meetuphub.exceptions.DatabaseException;
import meetuphub.exceptions.FileException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {

    private DBUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        String dbURL = null;
        String dbUsername = "postgres";
        String dbPassword = "adoleva";

        FileInputStream fis = null;
        Properties properties = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/config.properties");
            properties.load(fis);
            fis.close();
            dbURL = properties.getProperty("db.host");
            dbUsername = properties.getProperty("db.username");
            dbPassword = properties.getProperty("db.password");

        } catch (FileNotFoundException e) {
            throw new FileException("Ошибка при попытке найти файл.");
        } catch (IOException e) {
            throw new FileException("Ошибка чтения файла.");
        }

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка подключения к базе данных.");
        }
        return connection;
    }


}
