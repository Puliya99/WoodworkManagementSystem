package lk.ijse.Woodwork.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemTm {

    private String itemCode;

    private String description;

    private Integer qty;

    private Double unitPrice;

}
