package lk.ijse.Woodwork.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Woodwork.bo.BOFactory;
import lk.ijse.Woodwork.bo.custom.CustomerBo;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.CustomerDTO;
import lk.ijse.Woodwork.dto.tm.CustomerTm;
import lk.ijse.Woodwork.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerFormController implements Initializable {

    @FXML
    private AnchorPane addCustomer;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colCustomerName;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtCusContactNo;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtSearch;
    CustomerBo customerBo = (CustomerBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    @FXML
    void btnAddNewOnAction(ActionEvent event) {
        String cusId = txtCusId.getText();
        String custName = txtCusName.getText();
        String custAddress = txtCusAddress.getText();
        String custContactNo = txtCusContactNo.getText();
        if (Regex.validateCustomerCID(cusId) && Regex.validateName(custName) && Regex.validateAddress(custAddress) && Regex.validateMobile(custContactNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = customerBo.saveCustomer(new CustomerDTO(cusId, custName, custAddress, custContactNo));
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Customer saved!!!").show();
                        getAll();
                        txtSearch.setText(null);
                        txtCusId.setText(null);
                        txtCusName.setText(null);
                        txtCusAddress.setText(null);
                        txtCusContactNo.setText(null);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addCustomer.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtCusId.setText(null);
        txtCusName.setText(null);
        txtCusAddress.setText(null);
        txtCusContactNo.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtCusId.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = customerBo.deleteCustomer(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer DELETED").show();
                    txtSearch.setText(null);
                    txtCusId.setText(null);
                    txtCusName.setText(null);
                    txtCusAddress.setText(null);
                    txtCusContactNo.setText(null);
                    getAll();
                }else {
                    new Alert(Alert.AlertType.WARNING, "CUSTOMER NOT DELETED").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Sql error").show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String cusId = txtSearch.getText();
        try {
            CustomerDTO customer = customerBo.searchCustomer(cusId);
            if (customer != null) {
                txtCusId.setText(customer.getCustId());
                txtCusName.setText(customer.getCustName());
                txtCusAddress.setText(customer.getCustAddress());
                txtCusContactNo.setText(customer.getCustContactNo());
            } else {
                new Alert(Alert.AlertType.WARNING, "no customer found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String custId = txtCusId.getText();
        String custName = txtCusName.getText();
        String custAddress = txtCusAddress.getText();
        String custContactNo = txtCusContactNo.getText();

        var customer = new CustomerDTO(custId, custName, custAddress, custContactNo);
        if (Regex.validateCustomerCID(custId) && Regex.validateName(custName) && Regex.validateAddress(custAddress) && Regex.validateMobile(custContactNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = customerBo.updateCustomer(new CustomerDTO(custId, custName, custAddress, custContactNo));
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! Customer Updated!").show();
                        getAll();
                        txtSearch.setText(null);
                        txtCusId.setText(null);
                        txtCusName.setText(null);
                        txtCusAddress.setText(null);
                        txtCusContactNo.setText(null);
                    } else {
                        new Alert(Alert.AlertType.WARNING, "CUSTOMER NOT UPDATE").show();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "oops! something happened!").show();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
        }
    }

    @FXML
    void txtCusAddressOnAction(ActionEvent event) {
        txtCusContactNo.requestFocus();
    }

    @FXML
    void txtCusIdOnAction(ActionEvent event) {
        txtCusName.requestFocus();
    }

    @FXML
    void txtCusNameOnAction(ActionEvent event) {
        txtCusAddress.requestFocus();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        btnSearch.fire();
    }


    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    @FXML
    void setCellValueFactory() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("custContactNo"));
    }

    @FXML
    void getAll() {
        try {
            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
            List<CustomerDTO> cusList = customerBo.getAllCustomer();

            for(CustomerDTO customer : cusList) {
                obList.add(new CustomerTm(
                        customer.getCustId(),
                        customer.getCustName(),
                        customer.getCustAddress(),
                        customer.getCustContactNo()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void btnReportOnAction(ActionEvent actionEvent) {

        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/2nd Semester/woodWork Project convert to Layeard/Woodwork/src/main/java/lk/ijse/Woodwork/report/CustomerReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void mouseClickGetDataOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        CustomerTm selectedItem = tblCustomer.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtCusId.setText(selectedItem.getCustId());
        txtCusName.setText(selectedItem.getCustName());
        txtCusAddress.setText(selectedItem.getCustAddress());
        txtCusContactNo.setText(selectedItem.getCustContactNo());
    }

}
