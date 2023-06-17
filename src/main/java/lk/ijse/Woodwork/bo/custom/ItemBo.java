package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.PurchaseOrderDTO;
import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    public boolean deleteItem(String itemCode) throws SQLException;

    public boolean saveItem(ItemDTO dto) throws SQLException;

    public ItemDTO searchItem(String itemId) throws SQLException;

    public boolean updateItem(ItemDTO item) throws SQLException;

    public List<ItemDTO> getAllItem() throws SQLException;

    public ItemDTO searchByIdItem(String code) throws SQLException;

    public List<String> getCodesItem() throws SQLException;

    public boolean updateQtyItem(List<PurchaseOrderDTO> purchaseOrderList) throws SQLException;

    public boolean updateQtyItem(PurchaseOrderDTO dto) throws SQLException;

    public boolean updateProductQtyItem(List<JobCardDTO> jobCardDTOList) throws SQLException;

    public boolean updateProductsQtyItem(JobCardDTO dto) throws SQLException;
}