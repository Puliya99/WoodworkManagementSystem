package lk.ijse.Woodwork.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String custId;

    private String custName;

    private String custAddress;

    private String custContactNo;
}
