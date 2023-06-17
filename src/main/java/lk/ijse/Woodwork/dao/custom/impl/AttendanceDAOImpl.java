package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.AttendanceDAO;
import lk.ijse.Woodwork.entity.Attendance;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAOImpl implements AttendanceDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Work_details WHERE empId = ?", empId);
    }

    @Override
    public boolean save(Attendance entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Work_details(empId, jobCardNo, date, description, workingHours, empSalary) VALUES(?, ?, ?, ?, ?, ?)", entity.getEmpId(), entity.getJobCardNo(), entity.getDate(),entity.getDescription(),entity.getWorkingHours(),entity.getEmpSalary());
    }

    @Override
    public boolean update(Attendance entity) throws SQLException {
        return SQLUtil.execute("UPDATE Work_details SET jobCardNo = ?, date = ?, description = ?, workingHours = ?, empSalary = ? WHERE empId = ?", entity.getJobCardNo(), entity.getDate(),entity.getDescription(),entity.getWorkingHours(),entity.getEmpSalary(),entity.getEmpId());
    }

    @Override
    public Attendance search(String empId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Work_details WHERE empId = ?", empId + "");
        rst.next();
        return new Attendance(empId + "", rst.getString("jobCardNo"), rst.getString("date"), rst.getString("description") ,rst.getInt("workingHours"),rst.getDouble("empSalary"));
    }

    @Override
    public List<Attendance> getAll() throws SQLException {
        ArrayList<Attendance> allAttendance = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Work_details");
        while (rst.next()){
            Attendance attendance = new Attendance(rst.getString("empId"),rst.getString("jobCardNo"),rst.getString("date"),rst.getString("description"),rst.getInt("workingHours"),rst.getDouble("empSalary"));
            allAttendance.add(attendance);
        }
        return allAttendance;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public List<String> getIds() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public Attendance searchById(String cusId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean updateQty(Attendance entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean updateProductsQty(Attendance entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean userVerified(Attendance entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean saveJob(String jobCardNo, Attendance entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean saveSupplier(Attendance dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Attendance dto) throws SQLException {
        return false;
    }

}
