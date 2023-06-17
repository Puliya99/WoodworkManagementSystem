package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.AttendanceBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.AttendanceDAO;
import lk.ijse.Woodwork.dto.AttendanceDTO;
import lk.ijse.Woodwork.entity.Attendance;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBOImpl implements AttendanceBo {
    AttendanceDAO attendanceDAO = (AttendanceDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ATTENDANCE);
    @Override
    public boolean deleteAttendance(String empId) throws SQLException {
        return attendanceDAO.delete(empId);
    }

    @Override
    public boolean saveAttendance(AttendanceDTO dto) throws SQLException {
        return attendanceDAO.save(new Attendance(dto.getEmpId(), dto.getJobCardNo(), dto.getDate(), dto.getDescription(), dto.getWorkingHours(), dto.getEmpSalary()));
    }

    @Override
    public boolean updateAttendance(AttendanceDTO attendance) throws SQLException {
        return attendanceDAO.update(new Attendance(attendance.getEmpId(), attendance.getJobCardNo(), attendance.getDate(), attendance.getDescription(), attendance.getWorkingHours(), attendance.getEmpSalary()));
    }

    @Override
    public AttendanceDTO searchAttendace(String empId) throws SQLException {
        Attendance attendance = attendanceDAO.search(empId);
        return new AttendanceDTO(attendance.getEmpId(),attendance.getJobCardNo(), attendance.getDate(), attendance.getDescription(), attendance.getWorkingHours(),attendance.getEmpSalary());
    }

    @Override
    public List<AttendanceDTO> getAllAttendace() throws SQLException {
        ArrayList<AttendanceDTO> allAttendance = new ArrayList<>();
        ArrayList<Attendance> all = (ArrayList<Attendance>) attendanceDAO.getAll();
        for (Attendance attendance : all){
            allAttendance.add(new AttendanceDTO(attendance.getEmpId(),attendance.getJobCardNo(), attendance.getDate(), attendance.getDescription(), attendance.getWorkingHours(),attendance.getEmpSalary()));
        }
        return allAttendance;
    }
}
