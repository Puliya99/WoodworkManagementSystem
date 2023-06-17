package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.SupplierDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.entity.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public boolean delete(String supplierId) throws SQLException {
        return SQLUtil.execute("DELETE FROM Supplier WHERE supplierId = ?",supplierId);
    }

    @Override
    public boolean save(Supplier entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Supplier(supplierId, empId, supplierName, supplierAddress, supplierContactNo) VALUES(?, ?, ?, ?, ?)", entity.getSupplierId(), entity.getEmpId(), entity.getSupplierName(), entity.getSupplierAddress(),entity.getSupplierContactNo());
    }

    @Override
    public boolean update(Supplier entity) throws SQLException {
        return SQLUtil.execute( "UPDATE Supplier SET empId = ?, supplierName = ?, supplierAddress = ?, supplierContactNo = ? WHERE supplierId = ?",entity.getEmpId(),entity.getSupplierName(),entity.getSupplierAddress(),entity.getSupplierContactNo(),entity.getSupplierId());
    }

    @Override
    public Supplier search(String supplierId) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier WHERE supplierId = ?", supplierId + "");
        rst.next();
        return new Supplier(supplierId + "", rst.getString("empId"),rst.getString("supplierName"), rst.getString("supplierAddress"), rst.getString("supplierContactNo"));
    }

    @Override
    public List<Supplier> getAll() throws SQLException {
        ArrayList<Supplier> allSupplier = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Supplier");
        while (rst.next()) {
            Supplier supplier = new Supplier(rst.getString("supplierId"), rst.getString("empId"),rst.getString("supplierName"), rst.getString("supplierAddress"), rst.getString("supplierContactNo"));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        return false;
    }

    @Override
    public List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT supplierId FROM Supplier";

        List<String> supplierId = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            supplierId.add(resultSet.getString(1));
        }
        return supplierId;
    }

    @Override
    public Supplier searchById(String SupplierId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Supplier WHERE SupplierId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, SupplierId);

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

    @Override
    public boolean updateQty(Supplier entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(Supplier entity) throws SQLException {
        return false;
    }

    @Override
    public boolean userVerified(Supplier entity) throws SQLException {
        return false;
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, Supplier entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(Supplier dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Supplier dto) throws SQLException {
        return false;
    }

}
