package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.AttendanceDTO;
import java.sql.SQLException;
import java.util.List;

public interface AttendanceBo extends SuperBo {
    public  boolean deleteAttendance(String empId) throws SQLException;

    public  boolean saveAttendance(AttendanceDTO dto) throws SQLException;

    public  boolean updateAttendance(AttendanceDTO attendance) throws SQLException;

    public AttendanceDTO searchAttendace(String empId) throws SQLException;

    public  List<AttendanceDTO> getAllAttendace() throws SQLException;

}
