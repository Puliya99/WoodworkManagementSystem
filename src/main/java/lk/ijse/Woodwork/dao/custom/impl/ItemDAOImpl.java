package lk.ijse.Woodwork.dao.custom.impl;

import lk.ijse.Woodwork.dao.SQLUtil;
import lk.ijse.Woodwork.dao.custom.ItemDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.entity.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean delete(String itemCode) throws SQLException {
        return SQLUtil.execute("DELETE FROM Item WHERE itemCode = ?",itemCode);
    }

    @Override
    public boolean save(Item entity) throws SQLException {
        return SQLUtil.execute("INSERT INTO Item(itemCode, description,qty, unitPrice) VALUES(?, ?, ?, ?)", entity.getItemCode(), entity.getDescription(), entity.getQty(), entity.getUnitPrice());
    }

    @Override
    public boolean update(Item entity) throws SQLException {
        return SQLUtil.execute( "UPDATE Item SET description = ?, qty = ?, unitPrice = ? WHERE itemCode = ?",entity.getDescription(),entity.getQty(),entity.getUnitPrice(),entity.getItemCode());
    }

    @Override
    public Item search(String itemCode) throws SQLException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item WHERE itemCode = ?", itemCode + "");
        rst.next();
        return new Item(itemCode + "", rst.getString("description"), rst.getInt("qty"), rst.getDouble("unitPrice"));
    }

    @Override
    public List<Item> getAll() throws SQLException {
        ArrayList<Item> allItems = new ArrayList<>();
        ResultSet rst = SQLUtil.execute("SELECT * FROM Item");
        while (rst.next()) {
            Item item = new Item(rst.getString("itemCode"), rst.getString("description"), rst.getInt("qty"), rst.getDouble("unitPrice"));
            allItems.add(item);
        }
        return allItems;
    }

    @Override
    public boolean empIdVerified(String empId) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT itemCode FROM Item";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    @Override
    public Item searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Item WHERE itemCode = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public boolean updateQty(Item entity) throws SQLException {
        String sql = "UPDATE Item SET qty = (qty+?) WHERE itemCode = ?";
        return SQLUtil.execute(sql,entity.getQty(),entity.getItemCode());
    }

    @Override
    public boolean updateProductsQty(Item entity) throws SQLException {
        String sql = "UPDATE Item SET qty = (qty-?) WHERE itemCode = ?";
        return SQLUtil.execute(sql,entity.getQty(),entity.getItemCode());
    }

    @Override
    public boolean userVerified(Item entity) throws SQLException {
        throw new UnsupportedOperationException("This Feature is not implemented yet");
    }

    @Override
    public String generateNextOrderId() throws SQLException {
        return null;
    }

    @Override
    public boolean saveJob(String jobCardNo, Item entity) throws SQLException {
        return false;
    }

    @Override
    public boolean saveSupplier(Item dto, LocalDate date, String supplierId) throws SQLException {
        return false;
    }

    @Override
    public boolean saveOrder(String orderId, String jobCardNo, String Description, Item dto) throws SQLException {
        return false;
    }

}
