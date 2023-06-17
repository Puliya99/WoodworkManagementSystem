package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.ForgotPasswordDAO;
import lk.ijse.Woodwork.entity.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ForgotPasswordDAOImpl implements ForgotPasswordDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean save(User entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return SQLUtil.execute("UPDATE User SET password = ? WHERE username = ?",entity.getPassword(),entity.getUserName());
    }

    @Override
    public User search(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public List<User> getAll() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean empIdVerified(String username) throws SQLException {
        ResultSet resultSet =  SQLUtil.execute("SELECT * FROM User WHERE username = ?", username);
        return resultSet.next() ? true : false;
    }

    @Override
    public List<String> getIds() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public User searchById(String cusId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean updateQty(User entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(User entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(User entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, User entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(User dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, User dto) throws SQLException {
        return false;
    }

}
