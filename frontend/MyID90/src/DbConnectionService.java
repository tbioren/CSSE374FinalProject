import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionService {
    private Connection connection;
    private final String url = "jdbc:sqlserver://${dbServer};databaseName=${dbName};" +
            "user=${user};password={${pass}};encrypt=true;trustServerCertificate=true";

    public DbConnectionService() {
        connection = null;
    }

    public boolean connect() {
        String fullUrl = url
                .replace("${dbServer}", "golem.csse.rose-hulman.edu")
                .replace("${dbName}", "ProjectDatabaseS2G4ScriptTest")
                .replace("${user}", "MyID90Application")
                .replace("${pass}", "Password123");
        try {
            connection = DriverManager.getConnection(fullUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection != null;
    }

    public Connection getConnection() {
        return connection;
    }
}
