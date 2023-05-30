package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.dto.PurchaseOrderDto;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierDetailsModel {

    public static boolean save(List<PurchaseOrderDto> purchaseOrderList, LocalDate date, String supplierId) throws SQLException {
        for(PurchaseOrderDto dto :  purchaseOrderList) {
            if(!saveSupplier(dto,date,supplierId)) {
                return false;
            }
        }
        return true;
    }

    private static boolean saveSupplier(PurchaseOrderDto dto, LocalDate date, String supplierId) throws SQLException {
        String sql = "INSERT INTO Supplier_details VALUES (?,?,?,?,?)";
        return CrudUtil.execute(sql,supplierId,dto.getCode(),date,dto.getQty(),dto.getAmount());
    }

}
