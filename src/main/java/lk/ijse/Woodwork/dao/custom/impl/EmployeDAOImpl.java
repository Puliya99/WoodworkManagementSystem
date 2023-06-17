package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.EmployeeDAO;
import lk.ijse.Woodwork.entity.Employee;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAOImpl implements EmployeeDAO {
    @Override
    public boolean delete(String empId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Employee WHERE empId = ?", empId);
    }

    @Override
    public boolean save(Employee entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Employee(empId, empName, userName, address, contact) VALUES(?, ?, ?, ?, ?)", entity.getEmpId(), entity.getEmpName(), entity.getUserName(), entity.getAddress(), entity.getAddress());
    }

    @Override
    public boolean update(Employee entity) throws SQLException {
        return SQLUtil.execute("UPDATE Employee SET empName = ?, userName = ?, address = ?, contact = ? WHERE empId = ?", entity.getEmpName(), entity.getUserName(), entity.getAddress(), entity.getAddress(), entity.getEmpId());
    }

    @Override
    public Employee search(String empId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee WHERE empId = ?", empId + "");
        rst.next();
        return new Employee(empId + "", rst.getString("empName"), rst.getString("userName"), rst.getString("address"), rst.getString("contact"));
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        ArrayList<Employee> allEmployees = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Employee");
        while (rst.next()) {
            Employee employee = new Employee(rst.getString("empId"), rst.getString("empName"), rst.getString("userName"), rst.getString("address"), rst.getString("contact"));
            allEmployees.add(employee);
        }
        return allEmployees;
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
    public Employee searchById(String cusId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public boolean updateQty(Employee entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(Employee entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(Employee entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }


    @Override
    public boolean saveJob(String jobCardNo, Employee entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(Employee dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Employee dto) throws SQLException {
        return false;
    }

}
