package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.UserDTO;
import java.sql.SQLException;

public interface CreateAccountBo extends SuperBo {
    public boolean empIdVerifiedAccount(String empId) throws SQLException;

    public boolean saveAccount(UserDTO dto) throws SQLException;

}
