package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.CustomerBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.CustomerDAO;
import lk.ijse.Woodwork.dto.CustomerDTO;
import lk.ijse.Woodwork.entity.Customer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBo {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException {
        return customerDAO.save(new Customer(dto.getCustId(), dto.getCustName(), dto.getCustAddress(), dto.getCustContactNo()));
    }

    @Override
    public boolean deleteCustomer(String custId) throws SQLException {
        return customerDAO.delete(custId);
    }

    @Override
    public CustomerDTO searchCustomer(String custId) throws SQLException {
        Customer c = customerDAO.search(custId);
        return new CustomerDTO(c.getCustId(),c.getCustName(),c.getCustAddress(),c.getCustContactNo());
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws SQLException {
        return customerDAO.update(new Customer(customer.getCustId(), customer.getCustName(), customer.getCustAddress(), customer.getCustContactNo()));
    }

    @Override
    public List<CustomerDTO> getAllCustomer() throws SQLException {
        List<CustomerDTO> allCustomers= new ArrayList<>();
        List<Customer> all = customerDAO.getAll();
        for (Customer c : all) {
            allCustomers.add(new CustomerDTO(c.getCustId(),c.getCustName(),c.getCustAddress(),c.getCustContactNo()));
        }
        return allCustomers;
    }

    @Override
    public List<String> getIdsCustomer() throws SQLException {
        List<String> allCustomer= new ArrayList<>();
        List<String> all = customerDAO.getIds();
        for (String item : all) {
            allCustomer.add(item);
        }
        return allCustomer;
    }

    @Override
    public CustomerDTO searchByIdCustomer(String cusId) throws SQLException {
        Customer customer = customerDAO.searchById(cusId);
        return new CustomerDTO(customer.getCustId(),customer.getCustName(),customer.getCustAddress(),customer.getCustContactNo());
    }
}
