package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.custom.ProductDetailsDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.entity.JobCard;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ProductDetailsDAOImpl implements ProductDetailsDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(JobCard entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(JobCard entity) throws SQLException {
        return false;
    }

    @Override
    public JobCard search(String empId) throws SQLException {
        return null;
    }

    @Override
    public List<JobCard> getAll() throws SQLException {
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
    public JobCard searchById(String cusId) throws SQLException {
        return null;
    }

    @Override
    public boolean updateQty(JobCard entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(JobCard entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(JobCard entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, JobCard entity) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO Product_details(jobCardNo, itemCode, Qty, productPrice) VALUES (?, ?, ?, ?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, jobCardNo);
        pstm.setString(2, entity.getItemCode());
        pstm.setInt(3, entity.getQty());
        pstm.setDouble(4, entity.getTotal());

        return pstm.executeUpdate() > 0;
    }

    @Override
    public boolean saveSupplier(JobCard dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, JobCard dto) throws SQLException {
        return false;
    }

}
