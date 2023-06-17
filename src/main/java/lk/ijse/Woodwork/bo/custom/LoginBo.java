package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.UserDTO;
import java.sql.SQLException;

public interface LoginBo extends SuperBo {
    public boolean userVerifiedUser(UserDTO dto) throws SQLException;
}
