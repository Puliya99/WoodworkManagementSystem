package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.PurchaseOrderBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.ItemDAO;
import lk.ijse.Woodwork.dao.custom.PurchaseOrderDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.PurchaseOrderDTO;
import lk.ijse.Woodwork.entity.Item;
import lk.ijse.Woodwork.entity.PurchaseOrder;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderBOImpl implements PurchaseOrderBo {
    PurchaseOrderDAO purchaseOrderDAO = (PurchaseOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PURCHASEORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean purchaseOrder(String itemCode, String supplierId, int suppliyQty, double suppliyAmount, List<PurchaseOrderDTO> purchaseOrderList, List<ItemDTO> itemList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = savePurchaseOrder(purchaseOrderList, LocalDate.now(),supplierId);
            if (isSaved) {
                boolean isUpdated = updateQty(itemList);
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
            con.setAutoCommit(true);
        }
    }

    @Override
    public boolean savePurchaseOrder(List<PurchaseOrderDTO> purchaseOrderDTOList, LocalDate date, String supplierId) throws SQLException {
        List<PurchaseOrder> purchaseOrder = new ArrayList<>();
        for (PurchaseOrderDTO purchaseOrderDTO : purchaseOrderDTOList){
            purchaseOrder.add(new PurchaseOrder(purchaseOrderDTO.getCode(),purchaseOrderDTO.getQty(),purchaseOrderDTO.getUnitPrice(),purchaseOrderDTO.getAmount()));
        }
        for(PurchaseOrder dto :  purchaseOrder) {
            if(!purchaseOrderDAO.saveSupplier(dto,date,supplierId)) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(List<ItemDTO> itemDTOList) throws SQLException {
        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDTO : itemDTOList){
            items.add(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getUnitPrice()));
        }
        for (Item item : items) {
            if(!itemDAO.updateQty(item)) {
                return false;
            }
        }
        return true;
    }

}
