package SignUp;

import Database.dbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class SignUpModel {
    private Connection myConnection;

    public SignUpModel() {
        try {
            myConnection = dbConnection.getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        if (myConnection == null) {
            System.exit(1);
        }
    }

    public boolean isDatabaseConnected() {

        return myConnection != null;
    }
}
