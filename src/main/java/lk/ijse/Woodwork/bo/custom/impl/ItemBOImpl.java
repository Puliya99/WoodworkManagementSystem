package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.ItemBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.ItemDAO;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.PurchaseOrderDTO;
import lk.ijse.Woodwork.entity.Item;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBOImpl implements ItemBo {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    @Override
    public boolean deleteItem(String itemCode) throws SQLException {
        return itemDAO.delete(itemCode);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException {
        return itemDAO.save(new Item(dto.getItemCode(),dto.getDescription(),dto.getQty(), dto.getUnitPrice()));
    }

    @Override
    public ItemDTO searchItem(String itemId) throws SQLException {
        Item item = itemDAO.search(itemId);
        return new ItemDTO(item.getItemCode(),item.getDescription(),item.getQty(),item.getUnitPrice());
    }

    @Override
    public boolean updateItem(ItemDTO item) throws SQLException {
        return itemDAO.update(new Item(item.getItemCode(),item.getDescription(),item.getQty(), item.getUnitPrice()));
    }

    @Override
    public List<ItemDTO> getAllItem() throws SQLException {
        List<ItemDTO> allItems= new ArrayList<>();
        List<Item> all = itemDAO.getAll();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getQty(),item.getUnitPrice()));
        }
        return allItems;
    }

    @Override
    public ItemDTO searchByIdItem(String code) throws SQLException {
        Item item = itemDAO.searchById(code);
        return new ItemDTO(item.getItemCode(),item.getDescription(),item.getQty(),item.getUnitPrice());
    }

    @Override
    public List<String> getCodesItem() throws SQLException {
        List<String> allItems= new ArrayList<>();
        List<String> all = itemDAO.getIds();
        for (String item : all) {
            allItems.add(item);
        }
        return allItems;
    }

    @Override
    public boolean updateQtyItem(List<PurchaseOrderDTO> purchaseOrderList) throws SQLException {
        return false;
    }

    @Override
    public boolean updateQtyItem(PurchaseOrderDTO dto) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductQtyItem(List<JobCardDTO> jobCardDTOList) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQtyItem(JobCardDTO dto) throws SQLException {
        return false;
    }
}
