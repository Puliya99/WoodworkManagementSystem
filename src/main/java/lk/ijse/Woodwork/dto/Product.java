package lk.ijse.Woodwork.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Product {

    private String jobCardNo;

    private String jobCardStartDate;

    private String description;

    private int qty;

    private Double unitPrice;

}
