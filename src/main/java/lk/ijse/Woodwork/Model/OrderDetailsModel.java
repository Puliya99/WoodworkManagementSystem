package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.dto.SaleOrderDto;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsModel {

    public static boolean save( String orderId, String jobCardNo, String Description, List<SaleOrderDto> salseOrderList) throws SQLException {
        for(SaleOrderDto dto :  salseOrderList) {
            if(!saveOrder(orderId,jobCardNo,Description,dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveOrder(String orderId, String jobCardNo, String Description,SaleOrderDto dto) throws SQLException {
        String sql = "INSERT INTO Order_details VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,orderId,jobCardNo,Description,dto.getQty(),dto.getAmount());
    }

}
