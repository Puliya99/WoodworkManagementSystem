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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Woodwork.bo.BOFactory;
import lk.ijse.Woodwork.bo.custom.EmployeeBo;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.EmployeeDTO;
import lk.ijse.Woodwork.dto.tm.EmployeeTm;
import lk.ijse.Woodwork.util.Regex;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {
    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private AnchorPane addEmployee;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpName;

    @FXML
    private TableColumn<?, ?> colEmpUserName;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmpAddress;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpName;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUserName;

    EmployeeBo employeeBo = (EmployeeBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);
    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtEmpId.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = employeeBo.deleteEmployee(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "EMPLOYEE DELETED").show();
                    txtSearch.setText(null);
                    txtEmpId.setText(null);
                    txtEmpName.setText(null);
                    txtUserName.setText(null);
                    txtEmpAddress.setText(null);
                    txtContactNo.setText(null);
                    getAll();
                }else {
                    new Alert(Alert.AlertType.WARNING, "EMPLOYEE NOT DELETED").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Sql error").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String empName = txtEmpName.getText();
        String userName = txtUserName.getText();
        String address = txtEmpAddress.getText();
        String contact = txtContactNo.getText();
        var employee = new EmployeeDTO(empId, empName, userName, address, contact);
        if (Regex.validateEmployeeCID(empId) && Regex.validateName(empName) && Regex.validateUsername(userName) && Regex.validateAddress(address) && Regex.validateMobile(contact)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = employeeBo.saveEmployee(new EmployeeDTO(empId, empName, userName, address, contact));
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Employee saved!!!").show();
                        txtSearch.setText(null);
                        txtEmpId.setText(null);
                        txtEmpName.setText(null);
                        txtUserName.setText(null);
                        txtEmpAddress.setText(null);
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
        String empId = txtSearch.getText();
        try {
            EmployeeDTO employee = employeeBo.searchEmployee(empId);
            if (employee != null) {
                txtEmpId.setText(employee.getEmpId());
                txtEmpName.setText(employee.getEmpName());
                txtUserName.setText(employee.getUserName());
                txtEmpAddress.setText(employee.getAddress());
                txtContactNo.setText(employee.getContact());
            } else {
                new Alert(Alert.AlertType.WARNING, "no Employee found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String empName = txtEmpName.getText();
        String userName = txtUserName.getText();
        String address = txtEmpAddress.getText();
        String contact = txtContactNo.getText();

        var employee = new EmployeeDTO(empId, empName, userName, address, contact);
        if (Regex.validateName(empName) && Regex.validateUsername(userName) && Regex.validateAddress(address) && Regex.validateMobile(contact)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = employeeBo.updateEmployee(new EmployeeDTO(empId, empName, userName, address, contact));
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! Employee Updated!").show();
                        txtSearch.setText(null);
                        txtEmpId.setText(null);
                        txtEmpName.setText(null);
                        txtUserName.setText(null);
                        txtEmpAddress.setText(null);
                        txtContactNo.setText(null);
                        getAll();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "EMPLOYEE NOT UPDATE").show();
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
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/2nd Semester/woodWork Project convert to Layeard/Woodwork/src/main/java/lk/ijse/Woodwork/report/employeeReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtEmpAddressOnAction(ActionEvent event) {
        txtContactNo.requestFocus();
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtEmpName.requestFocus();
    }

    @FXML
    void txtEmpNameOnAction(ActionEvent event) {
        txtUserName.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtEmpAddress.requestFocus();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        btnSearch.fire();
    }

    @FXML
    void btnBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage)  addEmployee.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtEmpId.setText(null);
        txtEmpName.setText(null);
        txtUserName.setText(null);
        txtEmpAddress.setText(null);
        txtContactNo.setText(null);
    }



    @FXML
    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    @FXML
    void getAll() {
        try {
            ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();
            List<EmployeeDTO> cusList = employeeBo.getAllEmployee();

            for(EmployeeDTO employee : cusList) {
                obList.add(new EmployeeTm(
                        employee.getEmpId(),
                        employee.getEmpName(),
                        employee.getUserName(),
                        employee.getAddress(),
                        employee.getContact()
                ));
            }
            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }


    @FXML
    void mouseClickGetDataOnAction(MouseEvent event) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtEmpId.setText(selectedItem.getEmpId());
        txtEmpName.setText(selectedItem.getEmpName());
        txtUserName.setText(selectedItem.getUserName());
        txtEmpAddress.setText(selectedItem.getAddress());
        txtContactNo.setText(selectedItem.getContact());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }
}
