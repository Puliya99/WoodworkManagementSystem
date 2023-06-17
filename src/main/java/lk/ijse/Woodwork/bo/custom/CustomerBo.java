package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.CustomerDTO;
import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBo {
    public boolean saveCustomer(CustomerDTO dto) throws SQLException;
    public boolean deleteCustomer(String custId) throws SQLException;
    public CustomerDTO searchCustomer(String custId) throws SQLException;
    public boolean updateCustomer(CustomerDTO customer) throws SQLException;

    public List<CustomerDTO> getAllCustomer() throws SQLException;

    public List<String> getIdsCustomer() throws SQLException;

    public CustomerDTO searchByIdCustomer(String cusId) throws SQLException;

}
