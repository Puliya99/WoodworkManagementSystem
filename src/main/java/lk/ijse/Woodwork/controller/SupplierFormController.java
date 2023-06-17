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
import lk.ijse.Woodwork.bo.custom.SupplierBo;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.SupplierDTO;
import lk.ijse.Woodwork.dto.tm.SupplierTm;
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

public class SupplierFormController implements Initializable {

    @FXML
    private AnchorPane addSupplier;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSupAddress;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;
    SupplierBo supplierBo = (SupplierBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addSupplier.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtSupId.setText(null);
        txtEmpId.setText(null);
        txtSupName.setText(null);
        txtSupAddress.setText(null);
        txtContactNo.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtSupId.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = supplierBo.deleteSupplier(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Supplier DELETED").show();
                    txtSearch.setText(null);
                    txtSupId.setText(null);
                    txtEmpId.setText(null);
                    txtSupName.setText(null);
                    txtSupAddress.setText(null);
                    txtContactNo.setText(null);
                    getAll();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier NOT DELETED").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Sql error").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String supplierId = txtSupId.getText();
        String empId = txtEmpId.getText();
        String supplierName = txtSupName.getText();
        String supplierAddress = txtSupAddress.getText();
        String supplierContactNo = txtContactNo.getText();
        if (Regex.validateSupplierCID(supplierId) && Regex.validateEmployeeCID(empId) && Regex.validateName(supplierName) && Regex.validateAddress(supplierAddress) && Regex.validateMobile(supplierContactNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = supplierBo.saveSupplier(new SupplierDTO(supplierId, empId, supplierName, supplierAddress, supplierContactNo));
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Supplier saved!!!").show();
                        txtSearch.setText(null);
                        txtSupId.setText(null);
                        txtEmpId.setText(null);
                        txtSupName.setText(null);
                        txtSupAddress.setText(null);
                        txtContactNo.setText(null);
                        getAll();
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
    void btnSearchOnAction(ActionEvent event) {
        String supplierId = txtSearch.getText();
        try {
            SupplierDTO supplier = supplierBo.searchSupplier(supplierId);
            if (supplier != null) {
                txtSupId.setText(supplier.getSupplierId());
                txtEmpId.setText(supplier.getEmpId());
                txtSupName.setText(supplier.getSupplierName());
                txtSupAddress.setText(supplier.getSupplierAddress());
                txtContactNo.setText(supplier.getSupplierContactNo());
            } else {
                new Alert(Alert.AlertType.WARNING, "no Supplier found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String supplierId = txtSupId.getText();
        String empId = txtEmpId.getText();
        String supplierName = txtSupName.getText();
        String supplierAddress = txtSupAddress.getText();
        String supplierContactNo = txtContactNo.getText();


         if (Regex.validateSupplierCID(supplierId) && Regex.validateEmployeeCID(empId) && Regex.validateName(supplierName) && Regex.validateAddress(supplierAddress) && Regex.validateMobile(supplierContactNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = supplierBo.updateSupplier(new SupplierDTO(supplierId, empId, supplierName, supplierAddress, supplierContactNo));
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! Supplier Updated!").show();
                        txtSearch.setText(null);
                        txtSupId.setText(null);
                        txtEmpId.setText(null);
                        txtSupName.setText(null);
                        txtSupAddress.setText(null);
                        txtContactNo.setText(null);
                        getAll();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "SUPPLIER NOT UPDATE").show();
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
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/2nd Semester/woodWork Project convert to Layeard/Woodwork/src/main/java/lk/ijse/Woodwork/report/SupplierReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
//            JasperPrintManager.printReport(jasperPrint, true);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtSupName.requestFocus();
    }

    @FXML
    void txtSupAddressOnAction(ActionEvent event) {
        txtContactNo.requestFocus();
    }

    @FXML
    void txtSupIdOnAction(ActionEvent event) {
        txtEmpId.requestFocus();
    }

    @FXML
    void txtSupNameOnAction(ActionEvent event) {
        txtSupAddress.requestFocus();
    }

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("supplierContactNo"));
    }

    void getAll() {
        try {
            ObservableList<SupplierTm> obList = FXCollections.observableArrayList();
            List<SupplierDTO> cusList = supplierBo.getAllSupplier();

            for(SupplierDTO supplier : cusList) {
                obList.add(new SupplierTm(
                        supplier.getSupplierId(),
                        supplier.getEmpId(),
                        supplier.getSupplierName(),
                        supplier.getSupplierAddress(),
                        supplier.getSupplierContactNo()
                ));
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    public void mouseClickGetDataOnAction(javafx.scene.input.MouseEvent mouseEvent) {
        SupplierTm selectedItem = tblSupplier.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtSupId.setText(selectedItem.getSupplierId());
        txtEmpId.setText(selectedItem.getEmpId());
        txtSupName.setText(selectedItem.getSupplierName());
        txtSupAddress.setText(selectedItem.getSupplierAddress());
        txtContactNo.setText(selectedItem.getSupplierContactNo());
    }

}
