package lk.ijse.Woodwork.dao.custom;

import lk.ijse.Woodwork.dao.CrudDAO;
import lk.ijse.Woodwork.entity.User;

import java.sql.SQLException;

public interface CreateAccountDAO extends CrudDAO<User> {
    public boolean empIdVerified(String empId) throws SQLException;
}
