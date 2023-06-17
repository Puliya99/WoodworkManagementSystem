package lk.ijse.Woodwork.dto;

import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SaleOrderDetailDTO {
    String orderId;
    String custId;
    LocalDate placeOrderDate;
    double orderAmount;
}
