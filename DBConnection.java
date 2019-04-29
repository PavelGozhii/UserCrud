import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String url = "jdbc:postgresql://localhost:5432/hw14";
    private static String user = "postgres";
    private static String password = "root";

    public static Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection to the PostgreSQL server successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
