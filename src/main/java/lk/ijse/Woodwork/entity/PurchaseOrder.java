package lk.ijse.Woodwork.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PurchaseOrder {
    String code;

    Integer qty;

    Double unitPrice;

    Double amount;
}
