package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.ForgotPasswordBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.ForgotPasswordDAO;
import lk.ijse.Woodwork.dto.UserDTO;
import lk.ijse.Woodwork.entity.User;

import java.sql.SQLException;

public class ForgotPasswordBOImpl implements ForgotPasswordBo {
    ForgotPasswordDAO forgotPasswordDAO = (ForgotPasswordDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.FROGETPASSWORD);
    @Override
    public boolean userVerifiedUserName(String username) throws SQLException {
        return forgotPasswordDAO.empIdVerified(username);
    }

    @Override
    public boolean updatePassword(UserDTO user) throws SQLException {
        return forgotPasswordDAO.update(new User(user.getUserName(),user.getPassword()));
    }
}
