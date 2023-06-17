package lk.ijse.Woodwork.bo.custom.impl;

import lk.ijse.Woodwork.bo.custom.JobCardBo;
import lk.ijse.Woodwork.dao.DAOFactory;
import lk.ijse.Woodwork.dao.custom.*;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.JobCardDetailsDTO;
import lk.ijse.Woodwork.entity.Item;
import lk.ijse.Woodwork.entity.JobCard;
import lk.ijse.Woodwork.entity.JobCardDetails;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobCardBOImpl implements JobCardBo {
    JobCardDAO jobCardDAO = (JobCardDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.JOBCARD);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    JobCardDetailsDAO jobCardDetailsDAO = (JobCardDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.JOBCARDDETAILS);
    ProductDetailsDAO productDetailsDAO = (ProductDetailsDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PRODUCTDETAILS);

    public boolean jobCard(String idCode, String jobCardNo, List<JobCardDTO> jobCardDTOList,List<ItemDTO> itemList) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();

            con.setAutoCommit(false);

            boolean isSaved = jobCardDetailsDAO.save(new JobCardDetails(idCode, jobCardNo, LocalDate.now()));
            if (isSaved) {
                boolean isUpdated = updateProductQty(itemList);
                if (isUpdated) {
                    boolean isProductDetailSaved = savejobcard(jobCardNo, jobCardDTOList);
                    if (isProductDetailSaved) {
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
    public String generateNextOrderId() throws SQLException {
        return jobCardDAO.generateNextOrderId();
    }

    @Override
    public boolean save(JobCardDetailsDTO dto) throws SQLException {
        return jobCardDetailsDAO.save(new JobCardDetails(dto.getIdCode(),dto.getJobCardNo(),dto.getDate()));
    }

    public boolean savejobcard(String jobCardNo, List<JobCardDTO> jobCardDTOList) throws SQLException {
        List<JobCard> jobCards = new ArrayList<>();
        for (JobCardDTO jobCardDTO : jobCardDTOList){
            jobCards.add(new JobCard(jobCardDTO.getItemCode(), jobCardDTO.getQty(), jobCardDTO.getTotal()));
        }

        for (JobCard jobCard : jobCards) {
            if (!productDetailsDAO.saveJob(jobCardNo, jobCard)) {
                return false;
            }
        }
        return true;
    }

    public boolean updateProductQty(List<ItemDTO> itemDTOList)throws SQLException {
        List<Item> items = new ArrayList<>();
        for (ItemDTO itemDTO : itemDTOList){
            items.add(new Item(itemDTO.getItemCode(),itemDTO.getDescription(),itemDTO.getQty(),itemDTO.getUnitPrice()));
        }
        for (Item item : items) {
            if(!itemDAO.updateProductsQty(item)) {
                return false;
            }
        }
        return true;
    }


}
