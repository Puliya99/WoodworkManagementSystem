package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    public static boolean userVerified(String username, String password) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM User WHERE username = ? and password = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, username);
        pstm.setString(2, password);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

}
