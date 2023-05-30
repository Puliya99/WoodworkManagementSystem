package lk.ijse.Woodwork.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class JobCardTm {

    private String itemCode;

    private String description;

    private Integer itemQty;

    private Double unitPrice;

    private Double total;

    private Button btn;

}
