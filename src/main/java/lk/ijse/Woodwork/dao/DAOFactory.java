package lk.ijse.Woodwork.dao;

import lk.ijse.Woodwork.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        ATTENDANCE,CREATEACCOUNT,CUSTOMER,EMPLOYEE,FROGETPASSWORD,ITEM,LOGIN,PRODUCT,SUPPLIER,JOBCARD,JOBCARDDETAILS,PRODUCTDETAILS,PURCHASEORDER,SALEORDER,SALEORDERDETAILS
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case CREATEACCOUNT:
                return new CreateAccountDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case EMPLOYEE:
                return new EmployeDAOImpl();
            case FROGETPASSWORD:
                return new ForgotPasswordDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case LOGIN:
                return new LoginDAOImpl();
            case PRODUCT:
                return new ProductDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case JOBCARD:
                return new JobCardDAOImpl();
            case JOBCARDDETAILS:
                return new JobCardDetailsDAOImpl();
            case PRODUCTDETAILS:
                return new ProductDetailsDAOImpl();
            case PURCHASEORDER:
                return new PurchaseOrderDAOImpl();
            case SALEORDER:
                return new SaleOrderDAOImpl();
            case SALEORDERDETAILS:
                return new SaleOrderDetailsDAOImpl();
            default:
                return null;
        }
    }
}
