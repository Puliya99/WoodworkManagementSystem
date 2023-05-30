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
import lk.ijse.Woodwork.Model.AttendanceModel;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Attendance;
import lk.ijse.Woodwork.dto.tm.AttendanceTm;
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

public class AttendanceFormController implements Initializable {

    @FXML
    private AnchorPane addAttendace;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colEmpId;

    @FXML
    private TableColumn<?, ?> colEmpSalary;

    @FXML
    private TableColumn<?, ?> colJobCardNo;

    @FXML
    private TableColumn<?, ?> colWorkingHours;

    @FXML
    private TableView<AttendanceTm> tblEmpSalary;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtEmpId;

    @FXML
    private TextField txtEmpSalary;

    @FXML
    private TextField txtJobCardNo;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtWorkingHourse;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addAttendace.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtEmpId.setText(null);
        txtJobCardNo.setText(null);
        txtDate.setText(null);
        txtDescription.setText(null);
        txtWorkingHourse.setText(null);
        txtEmpSalary.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtSearch.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = AttendanceModel.delete(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "EMPLOYEE ATTENDANCE DETAILS DELETED").show();
                    txtSearch.setText(null);
                    txtEmpId.setText(null);
                    txtJobCardNo.setText(null);
                    txtDate.setText(null);
                    txtDescription.setText(null);
                    txtWorkingHourse.setText(null);
                    txtEmpSalary.setText(null);
                   getAll();
                }else {
                    new Alert(Alert.AlertType.WARNING, "EMPLOYEE ATTENDANCE DETAILS NOT DELETED").show();
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
        String jobCardNo = txtJobCardNo.getText();
        String date = txtDate.getText();
        String description = txtDescription.getText();
        int workingHours = Integer.parseInt(txtWorkingHourse.getText());
        double empSalary = Double.parseDouble(txtEmpSalary.getText());
        if (Regex.validateEmployeeCID(empId) && Regex.validateProductCID(jobCardNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = AttendanceModel.save(empId, jobCardNo, date, description, workingHours,empSalary);
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Attendance saved!!!").show();
                        txtSearch.setText(null);
                        txtEmpId.setText(null);
                        txtJobCardNo.setText(null);
                        txtDate.setText(null);
                        txtDescription.setText(null);
                        txtWorkingHourse.setText(null);
                        txtEmpSalary.setText(null);
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
    void btnUpdateOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String jobCardNo = txtJobCardNo.getText();
        String date = txtDate.getText();
        String description = txtDescription.getText();
        int workingHours = Integer.parseInt(txtWorkingHourse.getText());
        double empSalary = Double.parseDouble(txtEmpSalary.getText());

        var attendance = new Attendance(empId, jobCardNo, date, description, workingHours, empSalary);
        if (Regex.validateEmployeeCID(empId) && Regex.validateProductCID(jobCardNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = AttendanceModel.update(attendance);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! ATTENDANCE DETAILS UPDATED!").show();
                        txtSearch.setText(null);
                        txtEmpId.setText(null);
                        txtJobCardNo.setText(null);
                        txtDate.setText(null);
                        txtDescription.setText(null);
                        txtWorkingHourse.setText(null);
                        txtEmpSalary.setText(null);
                        getAll();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "ATTENDANCE DETAILS NOT UPDATE").show();
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
    void btnSearchOnAction(ActionEvent event) {
        String empId = txtSearch.getText();
        try {
            Attendance attendance = AttendanceModel.search(empId);
            if (attendance != null) {
                txtEmpId.setText(attendance.getEmpId());
                txtJobCardNo.setText(attendance.getJobCardNo());
                txtDate.setText(attendance.getDate());
                txtDescription.setText(attendance.getDescription());
                txtWorkingHourse.setText(String.valueOf(attendance.getWorkingHours()));
                txtEmpSalary.setText(String.valueOf(attendance.getEmpSalary()));
            } else {
                new Alert(Alert.AlertType.WARNING, "NOT ATTENDANCE DETAILS FOUND :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/Final Project/Woodwork/src/main/java/lk/ijse/Woodwork/report/salaryAndAttendaceReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void txtDateOnAction(ActionEvent event) {
        txtDescription.requestFocus();
    }

    @FXML
    void txtDescriptionOnAction(ActionEvent event) {
        txtWorkingHourse.requestFocus();
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtJobCardNo.requestFocus();
    }

    @FXML
    void txtJobCardNoOnAction(ActionEvent event) {
        txtDate.requestFocus();
    }

    @FXML
    void txtWorkingHourseOnAction(ActionEvent event) {
        txtEmpSalary.requestFocus();
    }

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    @FXML
    void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colJobCardNo.setCellValueFactory(new PropertyValueFactory<>("jobCardNo"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colWorkingHours.setCellValueFactory(new PropertyValueFactory<>("workingHours"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));
    }

    @FXML
    void getAll() {
        try {
            ObservableList<AttendanceTm> obList = FXCollections.observableArrayList();
            List<Attendance> cusList = AttendanceModel.getAll();

            for(Attendance attendance : cusList) {
                obList.add(new AttendanceTm(
                        attendance.getEmpId(),
                        attendance.getJobCardNo(),
                        attendance.getDate(),
                        attendance.getDescription(),
                        attendance.getWorkingHours(),
                        attendance.getEmpSalary()
                ));
            }
            tblEmpSalary.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    @FXML
    void mouseClickGetDataOnAction(MouseEvent event) {
        AttendanceTm selectedItem = tblEmpSalary.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtEmpId.setText(selectedItem.getEmpId());
        txtJobCardNo.setText(selectedItem.getJobCardNo());
        txtDate.setText(selectedItem.getDate());
        txtDescription.setText(selectedItem.getDescription());
        txtWorkingHourse.setText(String.valueOf(selectedItem.getWorkingHours()));
        txtEmpSalary.setText(String.valueOf(selectedItem.getEmpSalary()));
    }

}
