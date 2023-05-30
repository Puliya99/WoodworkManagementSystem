package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Attendance;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceModel {

    public static boolean delete(String empId) throws SQLException {
        String sql = "DELETE FROM Work_details WHERE empId = ?";
        return CrudUtil.execute(sql,empId);
    }

    public static boolean save(String empId, String jobCardNo, String date, String description, int workingHours, double empSalary) throws SQLException {
        String sql = "INSERT INTO Work_details(empId, jobCardNo, date, description, workingHours, empSalary) VALUES(?, ?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, empId, jobCardNo, date, description, workingHours, empSalary);
    }

    public static boolean update(Attendance attendance) throws SQLException {
        String sql = "UPDATE Work_details SET jobCardNo = ?, date = ?, description = ?, workingHours = ?, empSalary = ? WHERE empId = ?";
        return CrudUtil.execute(sql, attendance.getJobCardNo(), attendance.getDate(), attendance.getDescription(), attendance.getWorkingHours(),attendance.getEmpSalary(), attendance.getEmpId());
    }

    public static Attendance search(String empId) throws SQLException {
        String sql = "SELECT * FROM Work_details WHERE empId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, empId);

        if (resultSet.next()) {
            String empIds = resultSet.getString(1);
            String jobCardNo = resultSet.getString(2);
            String date = resultSet.getString(3);
            String description = resultSet.getString(4);
            int workingHours = resultSet.getInt(5);
            double empSalary = resultSet.getInt(6);

            return new Attendance(empIds, jobCardNo, date, description, workingHours, empSalary);
        }
        return null;
    }

    public static List<Attendance> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Work_details";

        List<Attendance> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Attendance(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDouble(6)

            ));
        }
        return data;
    }

}
