package lk.ijse.Woodwork.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface CrudDAO<T> extends SuperDAO{
    public  boolean delete(String empId) throws SQLException;

    public  boolean save(T entity) throws SQLException;

    public  boolean update(T entity) throws SQLException;

    public T search(String empId) throws SQLException;

    public List<T> getAll() throws SQLException;

    public boolean empIdVerified(String empId) throws SQLException;

    public List<String> getIds() throws SQLException;

    public T searchById(String cusId) throws SQLException;

    public boolean updateQty(T entity) throws SQLException;

    public boolean updateProductsQty(T entity) throws SQLException;

    public boolean userVerified(T entity) throws SQLException;

    public String generateNextOrderId() throws SQLException;

    public boolean saveJob(String jobCardNo, T entity) throws SQLException;

    public boolean saveSupplier(T dto, LocalDate date, String supplierId) throws SQLException;

    public boolean saveOrder(String orderId, String jobCardNo, String Description, T dto) throws SQLException;

}
