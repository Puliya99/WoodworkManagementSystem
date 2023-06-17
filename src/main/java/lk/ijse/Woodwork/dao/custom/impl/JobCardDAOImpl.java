package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.JobCardDAO;
import lk.ijse.Woodwork.entity.JobCard;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;



public class JobCardDAOImpl implements JobCardDAO {
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
        ResultSet rst = SQLUtil.execute("SELECT idCode FROM jobCard ORDER BY idCode DESC LIMIT 1");
        return rst.next() ? String.format("I%03d", (Integer.parseInt(rst.getString("idCode").replace("I", "")) + 1)) : "I001";
    }

    @Override
    public boolean saveJob(String jobCardNo, JobCard entity) throws SQLException {
        return false;
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
