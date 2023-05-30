package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.User;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordModel {

    public static boolean userVerified(String username) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM User WHERE username = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, username);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean update(User user) throws SQLException {
            String sql = "UPDATE User SET password = ? WHERE username = ?";
            return CrudUtil.execute(sql, user.getPassword(), user.getUserName());
    }

}
