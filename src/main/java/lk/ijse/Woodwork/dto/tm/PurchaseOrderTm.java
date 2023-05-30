package lk.ijse.Woodwork.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class PurchaseOrderTm {

    private String code;

    private String description;

    private Integer qty;

    private Double unitPrice;

    private Double total;

    private Button btn;

}
