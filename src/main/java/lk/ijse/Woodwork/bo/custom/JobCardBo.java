package lk.ijse.Woodwork.bo.custom;


import lk.ijse.Woodwork.bo.SuperBo;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.JobCardDetailsDTO;
import java.sql.SQLException;
import java.util.List;

public interface JobCardBo extends SuperBo {
    public boolean jobCard(String idCode, String jobCardNo, List<JobCardDTO> jobCardDTOList,List<ItemDTO> itemList) throws SQLException;

    public String generateNextOrderId() throws SQLException;

    public boolean save(JobCardDetailsDTO dto) throws SQLException;

    public boolean savejobcard(String jobCardNo, List<JobCardDTO> jobCardDTOList) throws SQLException;

    public boolean updateProductQty(List<ItemDTO> jobCardList)throws SQLException;
}
