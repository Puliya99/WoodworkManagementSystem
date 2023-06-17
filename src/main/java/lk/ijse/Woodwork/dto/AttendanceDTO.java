package lk.ijse.Woodwork.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AttendanceDTO implements Serializable {

    private String empId;

    private String jobCardNo;

    private String date;

    private String description;

    private int workingHours;

    private double empSalary;

}
