package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.ProductDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public boolean delete(String jobCardNo) throws SQLException {
        return SQLUtil.execute("DELETE FROM Product WHERE jobCardNo = ?",jobCardNo);
    }

    @Override
    public boolean save(Product entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Product(jobCardNo, jobCardStartDate, description, qty, unitPrice) VALUES(?, ?, ?, ?, ?)", entity.getJobCardNo(), entity.getJobCardStartDate(), entity.getDescription(), entity.getQty(),entity.getUnitPrice());
    }

    @Override
    public boolean update(Product entity) throws SQLException {
        return SQLUtil.execute( "UPDATE Product SET jobCardStartDate = ?, description = ?, qty = ?, unitPrice = ? WHERE jobCardNo = ?",entity.getJobCardStartDate(),entity.getDescription(),entity.getQty(),entity.getUnitPrice(),entity.getJobCardNo());
    }

    @Override
    public Product search(String jobCardNo) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Product WHERE jobCardNo = ?", jobCardNo + "");
        rst.next();
        return new Product(jobCardNo + "", rst.getString("jobCardStartDate"),rst.getString("description"), rst.getInt("qty"), rst.getDouble("unitPrice"));
    }

    @Override
    public List<Product> getAll() throws SQLException {
        ArrayList<Product> allProducts = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Product");
        while (rst.next()) {
            Product product = new Product(rst.getString("jobCardNo"), rst.getString("jobCardStartDate"),rst.getString("description"), rst.getInt("qty"), rst.getDouble("unitPrice"));
            allProducts.add(product);
        }
        return allProducts;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT jobCardNo FROM Product";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    @Override
    public Product searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Product WHERE jobCardNo = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            );
        }
        return null;
    }

    @Override
    public boolean updateQty(Product entity) throws SQLException {
        return false;
    }

    @Override
    public boolean updateProductsQty(Product entity) throws SQLException {
        String sql = "UPDATE Product SET qty = (qty-?) WHERE jobCardNo = ?";
        return SQLUtil.execute(sql,entity.getQty(),entity.getJobCardNo());
    }

    @Override
    public boolean userVerified(Product entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }
    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, Product entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(Product dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Product dto) throws SQLException {
        return false;
    }


}
