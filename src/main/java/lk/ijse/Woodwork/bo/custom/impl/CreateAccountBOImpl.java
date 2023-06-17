package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.CreateAccountBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.CreateAccountDAO;
import lk.ijse.Woodwork.dto.UserDTO;
import lk.ijse.Woodwork.entity.User;

import java.sql.SQLException;

public class CreateAccountBOImpl implements CreateAccountBo {
    CreateAccountDAO createAccountDAO = (CreateAccountDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CREATEACCOUNT);
    @Override
    public boolean empIdVerifiedAccount(String empId) throws SQLException {
        return createAccountDAO.empIdVerified(empId);
    }

    @Override
    public boolean saveAccount(UserDTO dto) throws SQLException {
        return createAccountDAO.save(new User(dto.getUserName(),dto.getPassword()));
    }
}
