package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Product;
import lk.ijse.Woodwork.dto.SaleOrderDto;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {

    public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT jobCardNo FROM Product";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static Product searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Product WHERE jobCardNo = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

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

    public static boolean updateQty(List<SaleOrderDto> saleOrderList) throws SQLException {
        for (SaleOrderDto dto : saleOrderList) {
            if(!updateItemQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateItemQty(SaleOrderDto dto) throws SQLException {
        String sql = "UPDATE Product SET qty = (qty-?) WHERE jobCardNo = ?";
        return CrudUtil.execute(sql,dto.getQty(),dto.getCode());
    }

    public static boolean delete(String jobCardNo) throws SQLException {
        String sql = "DELETE FROM Product WHERE jobCardNo = ?";
        return CrudUtil.execute(sql,jobCardNo);
    }

    public static boolean save(String jobCardNo, String jobCardStartDate, String description, int qty, Double orderAmount) throws SQLException {
        String sql = "INSERT INTO Product(jobCardNo, jobCardStartDate, description, qty, unitPrice) VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.execute(sql, jobCardNo, jobCardStartDate, description, qty, orderAmount);
    }

    public static Product search(String jobCardNo) throws SQLException {
        String sql = "SELECT * FROM Product WHERE jobCardNo = ?";
        ResultSet resultSet = CrudUtil.execute(sql, jobCardNo);

        if (resultSet.next()) {
            String jobCardCode = resultSet.getString(1);
            String jobCardStartDate = resultSet.getString(2);
            String description = resultSet.getString(3);
            Integer qty = resultSet.getInt(4);
            Double unitPrice = resultSet.getDouble(5);
            return new Product(jobCardCode, jobCardStartDate, description, qty, unitPrice);
        }
        return null;
    }

    public static boolean update(Product product) throws SQLException {
        String sql = "UPDATE Product SET jobCardStartDate = ?, description = ?, qty = ?, unitPrice = ? WHERE jobCardNo = ?";
        return CrudUtil.execute(sql, product.getJobCardStartDate(), product.getDescription(), product.getQty(), product.getUnitPrice(), product.getJobCardNo());
    }

    public static List<Product> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Product";

        List<Product> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Product(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getDouble(5)
            ));
        }
        return data;
    }

}
