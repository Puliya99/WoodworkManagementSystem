package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Item;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.PurchaseOrderDto;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static boolean delete(String itemCode) throws SQLException {
        String sql = "DELETE FROM Item WHERE itemCode = ?";
        return CrudUtil.execute(sql, itemCode);
    }

    public static boolean save(String itemCode, String description, int qty, Double unitPrice) throws SQLException {
        String sql = "INSERT INTO Item(itemCode, description,qty, unitPrice) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql, itemCode, description, qty, unitPrice);
    }

    public static Item search(String itemId) throws SQLException {
        String sql = "SELECT * FROM Item WHERE itemCode = ?";
        ResultSet resultSet = CrudUtil.execute(sql, itemId);

        if (resultSet.next()) {
            String itemCode = resultSet.getString(1);
            String description = resultSet.getString(2);
            Integer qty = resultSet.getInt(3);
            Double unitPrice = resultSet.getDouble(4);
            return new Item(itemCode, description, qty, unitPrice);
        }
        return null;
    }

    public static boolean update(Item item) throws SQLException {
        String sql = "UPDATE Item SET description = ?, qty = ?, unitPrice = ? WHERE itemCode = ?";
        return CrudUtil.execute(sql, item.getDescription(), item.getQty(), item.getUnitPrice(), item.getItemCode());
    }

    public static List<Item> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Item";

        List<Item> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return data;
    }

    public static Item searchById(String code) throws SQLException {
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

    public static List<String> getCodes() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> codes = new ArrayList<>();

        String sql = "SELECT itemCode FROM Item";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            codes.add(resultSet.getString(1));
        }
        return codes;
    }

    public static boolean updateQty(List<PurchaseOrderDto> purchaseOrderList) throws SQLException {
        for (PurchaseOrderDto dto : purchaseOrderList) {
            if(!updateItemQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateItemQty(PurchaseOrderDto dto) throws SQLException {
        String sql = "UPDATE Item SET qty = (qty+?) WHERE itemCode = ?";
        return CrudUtil.execute(sql,dto.getQty(),dto.getCode());
    }

    public static boolean updateProductQty(List<JobCardDTO> jobCardDTOList)throws SQLException {
        for (JobCardDTO dto : jobCardDTOList) {
            if(!updateProductsQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateProductsQty(JobCardDTO dto) throws SQLException {
        String sql = "UPDATE Item SET qty = (qty-?) WHERE itemCode = ?";
        return CrudUtil.execute(sql,dto.getQty(),dto.getItemCode());
    }

}
