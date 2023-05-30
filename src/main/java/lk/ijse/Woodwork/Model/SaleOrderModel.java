package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.SaleOrderDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SaleOrderModel {

    public static String generateNextOrderId() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT orderId FROM Place_order ORDER BY orderId DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            if (id < 10) {
                return "O00" + id;
            }
            return "O0"+id;
        }
        return "O001";
    }

    public static boolean saleOrder(String orderId, String jobCardNo, String Description, int productQty, double productAmount, List<SaleOrderDto> saleOrderList, String customerId, LocalDate date, String amount) throws SQLException {
        Connection con = null;
        con = DBConnection.getInstance().getConnection();
        try {

            con.setAutoCommit(false);

            boolean issave = SalesModel.save(orderId, customerId, date, amount);
            if (issave) {
                boolean isSaved = OrderDetailsModel.save(orderId, jobCardNo, Description, saleOrderList);
                if (isSaved) {

                    boolean isUpdated = ProductModel.updateQty(saleOrderList);
                    if (isUpdated) {
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

}
