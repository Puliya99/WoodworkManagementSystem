package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.JobCardDetailsDAO;
import lk.ijse.Woodwork.entity.JobCardDetails;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JobCardDetailsDAOImpl implements JobCardDetailsDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return false;
    }

    @Override
    public boolean save(JobCardDetails entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO jobCard(idCode, Date, jobCardNo) VALUES (?, ?, ?)", entity.getIdCode(), entity.getDate(), entity.getJobCardNo());
    }

    @Override
    public boolean update(JobCardDetails entity) throws SQLException {
        return false;
    }

    @Override
    public JobCardDetails search(String empId) throws SQLException {
        return null;
    }

    @Override
    public List<JobCardDetails> getAll() throws SQLException {
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
    public JobCardDetails searchById(String cusId) throws SQLException {
        return null;
    }

    @Override
    public boolean updateQty(JobCardDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(JobCardDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(JobCardDetails entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, JobCardDetails entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(JobCardDetails dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, JobCardDetails dto) throws SQLException {
        return false;
    }

}
