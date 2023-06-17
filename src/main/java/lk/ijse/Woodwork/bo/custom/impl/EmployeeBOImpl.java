package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.EmployeeBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.EmployeeDAO;
import lk.ijse.Woodwork.dto.EmployeeDTO;
import lk.ijse.Woodwork.entity.Employee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBo {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.save(new Employee(dto.getEmpId(), dto.getEmpName(), dto.getUserName(), dto.getAddress(), dto.getContact()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException {
        return employeeDAO.update(new Employee(dto.getEmpId(), dto.getEmpName(), dto.getUserName(), dto.getAddress(), dto.getContact()));
    }

    @Override
    public EmployeeDTO searchEmployee(String empId) throws SQLException {
        Employee emp = employeeDAO.search(empId);
        return new EmployeeDTO(emp.getEmpId(),emp.getEmpName(),emp.getUserName(),emp.getAddress(),emp.getContact());
    }

    @Override
    public boolean deleteEmployee(String empId) throws SQLException {
        return employeeDAO.delete(empId);
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() throws SQLException {
        List<EmployeeDTO> allEmployees= new ArrayList<>();
        List<Employee> all = employeeDAO.getAll();
        for (Employee emp : all) {
            allEmployees.add(new EmployeeDTO(emp.getEmpId(),emp.getEmpName(),emp.getUserName(),emp.getAddress(),emp.getContact()));
        }
        return allEmployees;
    }
}
