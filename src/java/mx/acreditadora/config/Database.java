package mx.acreditadora.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Conexion a MySQL.
 *
 * Orden de lectura de cada valor:
 *   1. Variable de entorno (DB_URL, DB_USER, DB_PASSWORD, MYSQLHOST, ...)
 *   2. Archivo db.properties en el classpath (src/java/db.properties, NO se sube a git)
 *   3. Valor por defecto
 */
public class Database {

    private static final Properties props = new Properties();

    static {
        try {
            // En Tomcat el driver no siempre se auto-registra desde WEB-INF/lib
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No se encontro el driver de MySQL", e);
        }

        try (InputStream in = Database.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer db.properties: " + e.getMessage());
        }
    }

    private static String getConfig(String key, String defaultValue) {
        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            value = props.getProperty(key, defaultValue);
        }

        return value;
    }

    private static final String HOST =
        getConfig("MYSQLHOST", "localhost");

    private static final String PORT =
        getConfig("MYSQLPORT", "3306");

    private static final String DATABASE =
        getConfig("MYSQLDATABASE", "Acreditadora");

    private static final String URL =
        getConfig(
            "DB_URL",
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
                + "?useSSL=false&serverTimezone=UTC"
        );

    private static final String USER =
        getConfig("DB_USER", getConfig("MYSQLUSER", "root"));

    private static final String PASSWORD =
        getConfig("DB_PASSWORD", getConfig("MYSQLPASSWORD", ""));

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
