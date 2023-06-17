package lk.ijse.Woodwork.entity;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalseOrderDetails {
    String orderId;
    String custId;
    LocalDate placeOrderDate;
    double orderAmount;
}
