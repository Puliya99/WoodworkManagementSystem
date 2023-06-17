package lk.ijse.Woodwork.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class JobCardDetailsDTO {
    String idCode;

    String jobCardNo;

    LocalDate date;


}
