package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.EmployeeDTO;
import java.sql.SQLException;
import java.util.List;

public interface EmployeeBo extends SuperBo {
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException;

    public boolean updateEmployee(EmployeeDTO dto) throws SQLException;

    public EmployeeDTO searchEmployee(String empId) throws SQLException;

    public boolean deleteEmployee(String empId) throws SQLException;

    public List<EmployeeDTO> getAllEmployee() throws SQLException;
}
