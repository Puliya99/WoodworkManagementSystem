package lk.ijse.Woodwork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PurchaseOrderDto {

    String code;

    Integer qty;

    Double unitPrice;

    Double amount;

}
