package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.PurchaseOrderDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface PurchaseOrderBo extends SuperBo {
    public boolean purchaseOrder(String itemCode, String supplierId, int suppliyQty, double suppliyAmount, List<PurchaseOrderDTO> purchaseOrderList, List<ItemDTO> itemList) throws SQLException;
    public boolean savePurchaseOrder(List<PurchaseOrderDTO> purchaseOrderDTOList, LocalDate date, String supplierId) throws SQLException;
    public boolean updateQty(List<ItemDTO> itemDTOList) throws SQLException;
}