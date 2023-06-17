package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.ProductBO;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.ProductDAO;;
import lk.ijse.Woodwork.dto.ProductDTO;
import lk.ijse.Woodwork.entity.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductBOImpl implements ProductBO {
    ProductDAO productDAO = (ProductDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCT);
    @Override
    public List<String> getCodesProduct() throws SQLException {
        List<String> allProduct= new ArrayList<>();
        List<String> all = productDAO.getIds();
        for (String product : all) {
            allProduct.add(product);
        }
        return allProduct;
    }

    @Override
    public ProductDTO searchByIdProduct(String code) throws SQLException {
        Product product = productDAO.searchById(code);
        return new ProductDTO(product.getJobCardNo(),product.getJobCardStartDate(),product.getDescription(),product.getQty(),product.getUnitPrice());
    }

    @Override
    public boolean deleteProduct(String jobCardNo) throws SQLException {
        return productDAO.delete(jobCardNo);
    }

    @Override
    public boolean saveProduct(ProductDTO dto) throws SQLException {
        return productDAO.save(new Product(dto.getJobCardNo(),dto.getJobCardStartDate(),dto.getDescription(),dto.getQty(),dto.getUnitPrice()));
    }

    @Override
    public ProductDTO searchProduct(String jobCardNo) throws SQLException {
        Product product = productDAO.search(jobCardNo);
        return new ProductDTO(product.getJobCardNo(),product.getJobCardStartDate(),product.getDescription(),product.getQty(),product.getUnitPrice());
    }

    @Override
    public boolean updateProduct(ProductDTO dto) throws SQLException {
        return productDAO.update(new Product(dto.getJobCardNo(),dto.getJobCardStartDate(),dto.getDescription(),dto.getQty(),dto.getUnitPrice()));
    }

    @Override
    public List<ProductDTO> getAllProduct() throws SQLException {
        List<ProductDTO> allProducts= new ArrayList<>();
        List<Product> all = productDAO.getAll();
        for (Product product : all) {
            allProducts.add(new ProductDTO(product.getJobCardNo(),product.getJobCardStartDate(),product.getDescription(),product.getQty(),product.getUnitPrice()));
        }
        return allProducts;
    }
}
