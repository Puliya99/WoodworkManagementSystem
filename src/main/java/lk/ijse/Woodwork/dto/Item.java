package lk.ijse.Woodwork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Item {

    private String itemCode;

    private String description;

    private Integer qty;

    private Double unitPrice;

}
