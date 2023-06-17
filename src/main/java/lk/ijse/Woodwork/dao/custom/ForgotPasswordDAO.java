package lk.ijse.Woodwork.dao.custom;

import lk.ijse.Woodwork.dao.CrudDAO;
import lk.ijse.Woodwork.dto.UserDTO;
import lk.ijse.Woodwork.entity.User;

import java.sql.SQLException;

public interface ForgotPasswordDAO extends CrudDAO<User> {
    public boolean empIdVerified(String empId) throws SQLException;
}
