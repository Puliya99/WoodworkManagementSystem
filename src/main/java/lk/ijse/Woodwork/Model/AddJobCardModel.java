package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddJobCardModel {

    public static boolean save(String idCode, String jobCardNo , LocalDate date) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO jobCard(idCode, Date, jobCardNo) VALUES (?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, idCode);
        pstm.setString(2, String.valueOf(date));
        pstm.setString(3, jobCardNo);

        return pstm.executeUpdate() > 0;
    }

    public static String splitIdCode(String currentIdCode) {
        if(currentIdCode != null) {
            String[] strings = currentIdCode.split("I0");
            int id = Integer.parseInt(strings[1]);
            id++;
            if (id < 10) {
                return "I00" + id;
            }
            return "I0"+id;
        }
        return "I001";
    }

}
