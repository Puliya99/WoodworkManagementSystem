package lk.ijse.Woodwork.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AttendanceTm {

    private String empId;

    private String jobCardNo;

    private String date;

    private String description;

    private int workingHours;

    private double empSalary;

}
