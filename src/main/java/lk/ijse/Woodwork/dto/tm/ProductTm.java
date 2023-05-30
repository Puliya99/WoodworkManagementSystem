package lk.ijse.Woodwork.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ProductTm {

    private String jobCardNo;

    private String jobCardStartDate;

    private String description;

    private int qty;

    private Double unitPrice;

}
