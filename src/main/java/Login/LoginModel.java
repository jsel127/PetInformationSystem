package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Database.dbConnection;

public class LoginModel {
    private Connection myConnection;
    public LoginModel() {
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

    public boolean isLogin(String theUser, String thePass) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE UserID = ? and Password = ?";
        try {
            pr = myConnection.prepareStatement(sql);
            pr.setString(1, theUser);
            pr.setString(2, thePass);

            rs = pr.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {

        }

        finally {
            {
                pr.close();
                rs.close();
            }
        }
        return false;
    }
}
