package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CreateAccountModel {
    public static boolean empIdVerified(String empId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM Employee WHERE empId = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, empId);

        ResultSet resultSet = pstm.executeQuery();

        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static boolean save(String userName, String password) throws SQLException {
        String sql = "INSERT INTO User(userName, password) VALUES(?, ?)";
        return CrudUtil.execute(sql, userName, password);
    }

}
