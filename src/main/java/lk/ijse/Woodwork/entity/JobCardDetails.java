package lk.ijse.Woodwork.entity;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobCardDetails {
    String idCode;

    String jobCardNo;

    LocalDate date;


}
