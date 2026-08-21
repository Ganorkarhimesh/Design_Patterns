import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:sqlite:D:/Pract4/Pract4.db";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL);
            System.out.println("Database connected successfully!");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
}