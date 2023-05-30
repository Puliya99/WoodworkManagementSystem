package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.JobCardDTO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static lk.ijse.Woodwork.Model.AddJobCardModel.splitIdCode;


public class JobCardModel {
    public static boolean jobCard(String idCode, String jobCardNo, List<JobCardDTO> jobCardDTOList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = AddJobCardModel.save(idCode, jobCardNo, LocalDate.now());
            if (isSaved) {
                boolean isUpdated = ItemModel.updateProductQty(jobCardDTOList);
                if (isUpdated) {
                    boolean isProductDetailSaved = ProductDetailsModel.save(jobCardNo, jobCardDTOList);
                    if (isProductDetailSaved) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT idCode FROM jobCard ORDER BY idCode DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitIdCode(resultSet.getString(1));
        }
        return splitIdCode(null);
    }

}
