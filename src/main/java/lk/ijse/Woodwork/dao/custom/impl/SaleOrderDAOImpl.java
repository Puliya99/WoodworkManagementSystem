package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.SaleOrderDAO;
import lk.ijse.Woodwork.entity.SaleOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SaleOrderDAOImpl implements SaleOrderDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public SaleOrder search(String empId) throws SQLException {
        return null;
    }

    @Override
    public List<SaleOrder> getAll() throws SQLException {
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
    public SaleOrder searchById(String cusId) throws SQLException {
        return null;
    }

    @Override
    public boolean updateQty(SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT orderId FROM Place_order ORDER BY orderId DESC LIMIT 1");
        return rst.next() ? String.format("O%03d", (Integer.parseInt(rst.getString("orderId").replace("O", "")) + 1)) : "O001";
    }

    @Override
    public boolean saveJob(String jobCardNo, SaleOrder entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(SaleOrder dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, SaleOrder dto) throws SQLException {
        String sql = "INSERT INTO Order_details VALUES (?,?,?,?,?)";
        return SQLUtil.execute(sql,orderId,jobCardNo,Description,dto.getQty(),dto.getAmount());
    }
}
