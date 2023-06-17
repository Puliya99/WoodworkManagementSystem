package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.SupplierBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.SupplierDAO;
import lk.ijse.Woodwork.dto.SupplierDTO;
import lk.ijse.Woodwork.entity.Supplier;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierBOImpl implements SupplierBo {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException {
        return supplierDAO.delete(supplierId);
    }

    @Override
    public boolean saveSupplier(SupplierDTO supplier) throws SQLException {
        return supplierDAO.save(new Supplier(supplier.getSupplierId(),supplier.getEmpId(),supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContactNo()));
    }

    @Override
    public SupplierDTO searchSupplier(String supId) throws SQLException {
        Supplier supplier = supplierDAO.search(supId);
        return new SupplierDTO(supplier.getSupplierId(),supplier.getEmpId(),supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContactNo());
    }

    @Override
    public boolean updateSupplier(SupplierDTO supplier) throws SQLException {
        return supplierDAO.update(new Supplier(supplier.getSupplierId(),supplier.getEmpId(),supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContactNo()));
    }

    @Override
    public List<SupplierDTO> getAllSupplier() throws SQLException {
        List<SupplierDTO> allSupplier= new ArrayList<>();
        List<Supplier> all = supplierDAO.getAll();
        for (Supplier supplier : all) {
            allSupplier.add(new SupplierDTO(supplier.getSupplierId(),supplier.getEmpId(),supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContactNo()));
        }
        return allSupplier;
    }

    @Override
    public List<String> getIdsSupplier() throws SQLException {
        List<String> allSupplier= new ArrayList<>();
        List<String> all = supplierDAO.getIds();
        for (String supplier : all) {
            allSupplier.add(supplier);
        }
        return allSupplier;
    }

    @Override
    public SupplierDTO searchByIdSupplier(String supplierId) throws SQLException {
        Supplier supplier = supplierDAO.searchById(supplierId);
        return new SupplierDTO(supplier.getSupplierId(),supplier.getEmpId(),supplier.getSupplierName(),supplier.getSupplierAddress(),supplier.getSupplierContactNo());
    }
}
