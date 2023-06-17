package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.SaleOrderDetailsDAO;
import lk.ijse.Woodwork.entity.SalseOrderDetails;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SaleOrderDetailsDAOImpl implements SaleOrderDetailsDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(SalseOrderDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Place_order(orderId, custId, placeOrderDate, orderAmount) VALUES (?, ?, ?, ?)", entity.getOrderId(), entity.getCustId(), entity.getPlaceOrderDate(), entity.getOrderAmount());
    }

    @Override
    public boolean update(SalseOrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public SalseOrderDetails search(String empId) throws SQLException {
        return null;
    }

    @Override
    public List<SalseOrderDetails> getAll() throws SQLException {
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
    public SalseOrderDetails searchById(String cusId) throws SQLException {
        return null;
    }

    @Override
    public boolean updateQty(SalseOrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(SalseOrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(SalseOrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, SalseOrderDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(SalseOrderDetails dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, SalseOrderDetails dto) throws SQLException {
        return false;
    }
}
