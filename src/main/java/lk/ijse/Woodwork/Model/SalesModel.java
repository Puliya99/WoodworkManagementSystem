package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SalesModel {

    public static boolean save(String oId, String custId, LocalDate date, String orderAmount) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Place_order(orderId, custId, placeOrderDate, orderAmount) VALUES (?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, oId);
        pstm.setString(2, custId);
        pstm.setString(3, String.valueOf(date));
        pstm.setString(4, orderAmount);

        return pstm.executeUpdate() > 0;
    }

}
