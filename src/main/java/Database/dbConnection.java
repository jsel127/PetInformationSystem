package Database;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class dbConnection {
    public static Connection getConnection() throws SQLException {
        try {
            String dbURL = "jdbc:mysql://127.0.0.1:3306/sellers_jasmine_beyene_selly_db";
            String dbUser = "";
            String dbPassword = "";
            return DriverManager.getConnection(
                    dbURL,
                    dbUser,
                    dbPassword
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
