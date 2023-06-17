package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.ProductDTO;
import lk.ijse.Woodwork.dto.SaleOrderDTO;
import java.sql.SQLException;
import java.util.List;

public interface ProductBO extends SuperBo {
    public List<String> getCodesProduct() throws SQLException;

    public ProductDTO searchByIdProduct(String code) throws SQLException;

    public boolean deleteProduct(String jobCardNo) throws SQLException;

    public boolean saveProduct(ProductDTO dto) throws SQLException;
    public ProductDTO searchProduct(String jobCardNo) throws SQLException;

    public boolean updateProduct(ProductDTO product) throws SQLException;

    public List<ProductDTO> getAllProduct() throws SQLException;

}
