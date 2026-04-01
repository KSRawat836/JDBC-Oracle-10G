import java.sql.*;

public class DBConnection {
    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            return DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:xe",
                "SYSTEM",   
                "123"         //PUT your own password
            );

        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
        }

        return null;
    }
}