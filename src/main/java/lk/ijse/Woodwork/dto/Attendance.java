package lk.ijse.Woodwork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Attendance {

    private String empId;

    private String jobCardNo;

    private String date;

    private String description;

    private int workingHours;

    private double empSalary;

}
