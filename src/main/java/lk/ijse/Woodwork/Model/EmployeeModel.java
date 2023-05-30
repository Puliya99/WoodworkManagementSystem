package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Employee;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static boolean save(String empId, String empName, String userName, String address, String contact) throws SQLException {
        String sql = "INSERT INTO Employee(empId, empName, userName, address, contact) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, empId, empName, userName, address, contact);
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE Employee SET empName = ?, userName = ?, address = ?, contact = ? WHERE empId = ?";
        return CrudUtil.execute(sql, employee.getEmpName(), employee.getUserName(), employee.getAddress(), employee.getContact(), employee.getEmpId());
    }

    public static Employee search(String empId) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE empId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, empId);

        if (resultSet.next()) {
            String empIds = resultSet.getString(1);
            String empName = resultSet.getString(2);
            String userName = resultSet.getString(3);
            String address = resultSet.getString(4);
            String contact = resultSet.getString(5);

            return new Employee(empIds, empName, userName, address, contact);
        }
        return null;
    }

    public static boolean delete(String empId) throws SQLException {
        String sql = "DELETE FROM Employee WHERE empId = ?";
        return CrudUtil.execute(sql,empId);
    }

    public static List<Employee> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Employee";

        List<Employee> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

}
