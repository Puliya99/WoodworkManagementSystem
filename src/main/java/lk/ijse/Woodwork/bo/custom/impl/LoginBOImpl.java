package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.LoginBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.LoginDAO;
import lk.ijse.Woodwork.dto.UserDTO;
import lk.ijse.Woodwork.entity.User;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBo {
    LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.LOGIN);
    @Override
    public boolean userVerifiedUser(UserDTO dto) throws SQLException {
        return loginDAO.userVerified(new User(dto.getUserName(), dto.getPassword()));
    }
}
