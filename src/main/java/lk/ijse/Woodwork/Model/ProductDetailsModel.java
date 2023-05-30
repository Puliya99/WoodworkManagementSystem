package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.JobCardDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDetailsModel {

    public static boolean save(String jobCardNo, List<JobCardDTO> jobCardDTOList) throws SQLException {
        for (JobCardDTO dto : jobCardDTOList) {
            if (!save(jobCardNo, dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String jobCardNo, JobCardDTO dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Product_details(jobCardNo, itemCode, Qty, productPrice) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, jobCardNo);
        pstm.setString(2, dto.getItemCode());
        pstm.setInt(3, dto.getQty());
        pstm.setDouble(4, dto.getTotal());

        return pstm.executeUpdate() > 0;

    }

}
