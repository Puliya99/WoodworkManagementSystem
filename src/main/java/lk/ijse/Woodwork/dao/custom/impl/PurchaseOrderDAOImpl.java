package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.PurchaseOrderDAO;
import lk.ijse.Woodwork.entity.PurchaseOrder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PurchaseOrderDAOImpl implements PurchaseOrderDAO {

    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public PurchaseOrder search(String empId) throws SQLException {
        return null;
    }

    @Override
    public List<PurchaseOrder> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        return false;
    }

    @Override
    public List<String> getIds() throws SQLException {
        return null;
    }

    @Override
    public PurchaseOrder searchById(String cusId) throws SQLException {
        return null;
    }

    @Override
    public boolean updateQty(PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, PurchaseOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(PurchaseOrder dto, LocalDate date, String supplierId) throws SQLException {
        String sql = "INSERT INTO Supplier_details VALUES (?,?,?,?,?)";
        return SQLUtil.execute(sql,supplierId,dto.getCode(),date,dto.getQty(),dto.getAmount());
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, PurchaseOrder dto) throws SQLException {
        return false;
    }

}
