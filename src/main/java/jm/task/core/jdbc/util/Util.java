package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private Util() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream =
                     Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(get("url"), get("username"), get("password"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

