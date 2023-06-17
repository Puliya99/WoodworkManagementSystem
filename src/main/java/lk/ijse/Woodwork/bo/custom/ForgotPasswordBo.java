package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.UserDTO;
import java.sql.SQLException;

public interface ForgotPasswordBo extends SuperBo {
    public boolean userVerifiedUserName(String username) throws SQLException;
    public boolean updatePassword(UserDTO user) throws SQLException;
}
