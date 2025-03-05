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

    public boolean isLogin(int theUserID, String thePass, String theUserType) throws Exception {
        PreparedStatement pr = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users WHERE UserID = ? AND Password = ? AND IsVeterinarian = ? AND IsCaretaker = ?";
        try {
            pr = myConnection.prepareStatement(sql);
            pr.setInt(1, theUserID);
            pr.setString(2, thePass);
            pr.setBoolean(3, theUserType == "Veterinarian");
            pr.setBoolean(4, theUserType == "Caretaker");


            rs = pr.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
