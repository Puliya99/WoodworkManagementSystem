package lk.ijse.Woodwork.Model;

import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Customer;
import lk.ijse.Woodwork.util.CrudUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static boolean save(String custId, String custName, String custAddress, String custContactNo) throws SQLException {
        String sql = "INSERT INTO Customer(custId, custName,custAddress, custContactNo) VALUES(?, ?, ?, ?)";
        return CrudUtil.execute(sql, custId, custName, custAddress, custContactNo);
    }

    public static boolean delete(String custId) throws SQLException {
        String sql = "DELETE FROM Customer WHERE custId = ?";
        return CrudUtil.execute(sql,custId);
    }

    public static Customer search(String custId) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE custId = ?";
        ResultSet resultSet = CrudUtil.execute(sql, custId);

        if (resultSet.next()) {
            String cusId = resultSet.getString(1);
            String custName = resultSet.getString(2);
            String custAddress = resultSet.getString(3);
            String custContactNo = resultSet.getString(4);
            return new Customer(cusId, custName, custAddress, custContactNo);
        }
        return null;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET custName = ?, custAddress = ?, custContactNo = ? WHERE custId = ?";
        return CrudUtil.execute(sql, customer.getCustName(), customer.getCustAddress(), customer.getCustContactNo(), customer.getCustId());
    }

    public static List<Customer> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer";

        List<Customer> data = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            data.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static List<String> getIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT custId FROM Customer";

        List<String> ids = new ArrayList<>();

        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while (resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }

    public static Customer searchById(String cusId) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM Customer WHERE custId = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, cusId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

}
