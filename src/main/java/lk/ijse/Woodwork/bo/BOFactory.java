package lk.ijse.Woodwork.bo;

import lk.ijse.Woodwork.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
        ATTENDANCE,CREATEACCOUNT,CUSTOMER,EMPLOYEE,FROGETPASSWORD,ITEM,LOGIN,PRODUCT,SUPPLIER,JOBCARD,PURCHASEORDER,SALSEORDER
    }

    public SuperBo getBO(BOTypes types){
        switch (types){
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case CREATEACCOUNT:
                return new CreateAccountBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case FROGETPASSWORD:
                return new ForgotPasswordBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case PRODUCT:
                return new ProductBOImpl();
            case SUPPLIER:
                return new SupplierBOImpl();
            case JOBCARD:
                return new JobCardBOImpl();
            case PURCHASEORDER:
                return new PurchaseOrderBOImpl();
            case SALSEORDER:
                return new SaleOrderBOImpl();
            default:
                return null;
        }
    }
}
