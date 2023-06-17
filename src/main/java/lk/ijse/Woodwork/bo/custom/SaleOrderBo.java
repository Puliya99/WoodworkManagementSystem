package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.ProductDTO;
import lk.ijse.Woodwork.dto.SaleOrderDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface SaleOrderBo extends SuperBo {
    public String generateNextSaleOrderId() throws SQLException;
    public boolean saleOrder(String orderId, String jobCardNo, String Description, int productQty, double productAmount, List<SaleOrderDTO> saleOrderDTOList, List<ProductDTO> productDTOList, String customerId, LocalDate date, double amount) throws SQLException;
    public boolean saveOrders(String orderId, String jobCardNo, String Description, List<SaleOrderDTO> salseOrderDTOList) throws SQLException;

    public boolean updateQty(List<ProductDTO> productDTOList) throws SQLException;
}
