package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.PurchaseOrderDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderModel {
    public static boolean purchaseOrder(String itemCode, String supplierId, int suppliyQty, double suppliyAmount, List<PurchaseOrderDto> purchaseOrderList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = SupplierDetailsModel.save(purchaseOrderList,LocalDate.now(),supplierId);
            if (isSaved) {
                boolean isUpdated = ItemModel.updateQty(purchaseOrderList);
                if (isUpdated) {
                    con.commit();
                    return true;
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            System.out.println("finally");
            con.setAutoCommit(true);
        }
    }

}
