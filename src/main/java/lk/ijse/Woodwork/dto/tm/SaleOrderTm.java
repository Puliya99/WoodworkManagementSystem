package lk.ijse.Woodwork.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SaleOrderTm {

    private String orderId;

    private String jobCardNumber;

    private String description;

    private Integer productQty;

    private Double unitProductPrice;

    private Double total;

    private Button btn;

}