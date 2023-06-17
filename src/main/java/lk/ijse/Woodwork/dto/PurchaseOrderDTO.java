package lk.ijse.Woodwork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PurchaseOrderDTO {

    String code;

    Integer qty;

    Double unitPrice;

    Double amount;

}
