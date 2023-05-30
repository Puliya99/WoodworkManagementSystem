package lk.ijse.Woodwork.util;

public class Regex {

        private static final String NAME_REGEX = "^[a-zA-Z]{4,60}$";
        private static final String ADDRESS_REGEX = "^[a-zA-Z0-9,/]{4,60}$";
        private static final String MOBILE_REGEX = "^\\+?\\d{10}$";

        private static final String USERNAME_REGEX = "^[A-Za-z0-9]{3,}$";

        private static final String PASSWORD_REGEX = "[aA-zZ0-9@]{8,20}$";

        private static final String CUSTOMER_ID_REGEX = "^[Cc][0-9]{3}$";

        private static final String SUPPLIER_ID_REGEX = "^[Ss][0-9]{3}$";

        private static final String EMPLOYEE_ID_REGEX = "^[Ee][0-9]{3}$";

        private static final String ITEM_ID_REGEX = "^[Ii][0-9]{3}$";

        private static final String PRODUCT_ID_REGEX = "^[Jj][0-9]{3}$";

        public static boolean validateName(String name) {
            return name.matches(NAME_REGEX);
        }

        public static boolean validateMobile(String mobile) {
            return mobile.matches(MOBILE_REGEX);
        }

        public static boolean validateUsername(String username) {
            return username.matches(USERNAME_REGEX);
        }

        public static boolean validatePassword(String password) {
            return password.matches(PASSWORD_REGEX);
        }

        public static boolean validateCustomerCID(String customerId){return customerId.matches(CUSTOMER_ID_REGEX); }

        public static boolean validateSupplierCID(String supplierId){return supplierId.matches(SUPPLIER_ID_REGEX); }

        public static boolean validateEmployeeCID(String employeeId){return employeeId.matches(EMPLOYEE_ID_REGEX); }

        public static boolean validateItemCID(String itemId){return itemId.matches(ITEM_ID_REGEX); }

        public static boolean validateProductCID(String productId){return productId.matches(PRODUCT_ID_REGEX); }

        public static boolean validateAddress(String address) {return address.matches(ADDRESS_REGEX);}


}
