package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.CustomerDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean delete(String custId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Customer WHERE custId = ?", custId);
    }

    @Override
    public boolean save(Customer entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Customer(custId, custName,custAddress, custContactNo) VALUES(?, ?, ?, ?)", entity.getCustId(), entity.getCustName(), entity.getCustAddress(), entity.getCustContactNo());
    }

    @Override
    public boolean update(Customer entity) throws SQLException {
        return SQLUtil.execute("UPDATE Customer SET custName = ?, custAddress = ?, custContactNo = ? WHERE custId = ?", entity.getCustName(), entity.getCustAddress(), entity.getCustContactNo(),  entity.getCustId());
    }

    @Override
    public Customer search(String custId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer WHERE custId = ?", custId + "");
        rst.next();
        return new Customer(custId + "", rst.getString("custName"), rst.getString("custAddress"), rst.getString("custContactNo"));
    }

    @Override
    public List<Customer> getAll() throws SQLException {
        ArrayList<Customer> allCustomers = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Customer");
        while (rst.next()) {
            Customer customer = new Customer(rst.getString("custId"), rst.getString("custName"), rst.getString("custAddress"), rst.getString("custContactNo"));
            allCustomers.add(customer);
        }
        return allCustomers;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT custId FROM Customer";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    @Override
    public Customer searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE custId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    @Override
    public boolean updateQty(Customer entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean updateProductsQty(Customer entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(Customer entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, Customer entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(Customer dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Customer dto) throws SQLException {
        return false;
    }

}
