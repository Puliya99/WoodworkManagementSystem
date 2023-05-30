package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Supplier;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {

    public static boolean delete(String supplierId) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE supplierId = ?";
        return CrudUtil.execute(sql,supplierId);
    }

    public static boolean save(String supplierId, String empId, String supplierName, String supplierAddress, String supplierContactNo) throws SQLException {
        String sql = "INSERT INTO Supplier(supplierId, empId, supplierName, supplierAddress, supplierContactNo) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, supplierId, empId, supplierName, supplierAddress, supplierContactNo);
    }

    public static Supplier search(String supId) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE supplierId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, supId);

        if (resultSet.next()) {
            String supplierId = resultSet.getString(1);
            String empId = resultSet.getString(2);
            String supplierName = resultSet.getString(3);
            String supplierAddress = resultSet.getString(4);
            String supplierContactNo = resultSet.getString(5);

            return new Supplier(supplierId, empId, supplierName, supplierAddress, supplierContactNo);
        }
        return null;
    }

    public static boolean update(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET empId = ?, supplierName = ?, supplierAddress = ?, supplierContactNo = ? WHERE supplierId = ?";
        return CrudUtil.execute(sql, supplier.getEmpId(), supplier.getSupplierName(), supplier.getSupplierAddress(), supplier.getSupplierContactNo(), supplier.getSupplierId());
    }

    public static List<Supplier> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier";

        List<Supplier> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT supplierId FROM Supplier";

        List<String> supplierId = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            supplierId.add(resultSet.getString(1));
        }
        return supplierId;
    }

    public static Supplier searchById(String supplierId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, supplierId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Supplier(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

}
