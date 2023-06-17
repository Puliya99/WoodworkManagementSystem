package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.SaleOrderBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.ProductDAO;
import lk.ijse.Woodwork.dao.custom.SaleOrderDAO;
import lk.ijse.Woodwork.dao.custom.SaleOrderDetailsDAO;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.ProductDTO;
import lk.ijse.Woodwork.dto.SaleOrderDTO;
import lk.ijse.Woodwork.entity.Product;
import lk.ijse.Woodwork.entity.SaleOrder;
import lk.ijse.Woodwork.entity.SalseOrderDetails;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SaleOrderBOImpl implements SaleOrderBo {
    SaleOrderDAO saleOrderDAO = (SaleOrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALEORDER);
    SaleOrderDetailsDAO saleOrderDetailsDAO = (SaleOrderDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SALEORDERDETAILS);
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    @Override
    public String generateNextSaleOrderId() throws SQLException {
        return saleOrderDAO.generateNextOrderId();
    }

    @Override
    public boolean saleOrder(String orderId, String jobCardNo, String Description, int productQty, double productAmount, List<SaleOrderDTO> saleOrderDTOList, List<ProductDTO> productDTOList, String customerId, LocalDate date, double amount) throws SQLException {
        Connection con = null;
        con = DBConnection.getInstance().getConnection();
        try {

            con.setAutoCommit(false);

            boolean issave = saleOrderDetailsDAO.save(new SalseOrderDetails(orderId, customerId, date, amount));
            if (issave) {
                boolean isSaved =saveOrders(orderId, jobCardNo, Description, saleOrderDTOList);
                if (isSaved) {

                    boolean isUpdated =updateQty(productDTOList);
                    if (isUpdated) {
                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            er.printStackTrace();
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

    @Override
    public boolean saveOrders(String orderId, String jobCardNo, String Description, List<SaleOrderDTO> salseOrderDTOList) throws SQLException {
        List<SaleOrder> saleOrders = new ArrayList<>();
        for (SaleOrderDTO saleOrderDTO : salseOrderDTOList){
            saleOrders.add(new SaleOrder(saleOrderDTO.getCode(), saleOrderDTO.getQty(), saleOrderDTO.getUnitPrice(),saleOrderDTO.getAmount()));
        }
        for(SaleOrder dto :  saleOrders) {
            if(!saleOrderDAO.saveOrder(orderId,jobCardNo,Description,dto)) {
                return false;
            }
        }
        return true;
    }

    public boolean updateQty(List<ProductDTO> productDTOList) throws SQLException {
        List<Product> products = new ArrayList<>();
        for (ProductDTO productDTO : productDTOList) {
            products.add(new Product(productDTO.getJobCardNo(),productDTO.getJobCardStartDate(),productDTO.getDescription(),productDTO.getQty(),productDTO.getUnitPrice()));
        }

        for (Product product : products) {
            if(!productDAO.updateProductsQty(product)) {
                return false;
            }
        }
        return true;
    }


}
