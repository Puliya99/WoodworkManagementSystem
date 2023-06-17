package lk.ijse.Woodwork.bo.custom;

import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.SupplierDTO;
import java.sql.SQLException;
import java.util.List;

public interface SupplierBo extends SuperBo {
    public boolean deleteSupplier(String supplierId) throws SQLException;

    public boolean saveSupplier(SupplierDTO supplier) throws SQLException;
    public SupplierDTO searchSupplier(String supId) throws SQLException;

    public boolean updateSupplier(SupplierDTO supplier) throws SQLException;

    public List<SupplierDTO> getAllSupplier() throws SQLException;

    public List<String> getIdsSupplier() throws SQLException;

    public SupplierDTO searchByIdSupplier(String supplierId) throws SQLException;
}
