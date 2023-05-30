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
import lk.ijse.Woodwork.Model.ProductModel;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Product;
import lk.ijse.Woodwork.dto.tm.ProductTm;
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

public class ProductFormController implements Initializable {

    @FXML
    private AnchorPane addProduct;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colJobCardNo;

    @FXML
    private TableColumn<?, ?> colJobCardStartDate;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ProductTm> tblProduct;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtJobCardNo;

    @FXML
    private TextField txtJobCardStartDate;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addProduct.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtJobCardNo.setText(null);
        txtJobCardStartDate.setText(null);
        txtDescription.setText(null);
        txtQty.setText(null);
        txtUnitPrice.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtJobCardNo.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = ProductModel.delete(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "PRODUCT DELETED").show();
                    txtSearch.setText(null);
                    txtJobCardNo.setText(null);
                    txtJobCardStartDate.setText(null);
                    txtDescription.setText(null);
                    txtQty.setText(null);
                    txtUnitPrice.setText(null);
                    getAll();
                }else {
                    new Alert(Alert.AlertType.WARNING, "PRODUCT NOT DELETED").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Sql error").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String jobCardNo = txtJobCardNo.getText();
        String jobCardStartDate = txtJobCardStartDate.getText();
        String description= txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        if (Regex.validateProductCID(jobCardNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = ProductModel.save(jobCardNo, jobCardStartDate, description, qty, unitPrice);
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Product saved!!!").show();
                        txtSearch.setText(null);
                        txtJobCardNo.setText(null);
                        txtJobCardStartDate.setText(null);
                        txtDescription.setText(null);
                        txtQty.setText(null);
                        txtUnitPrice.setText(null);
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
        String jobCardNo = txtSearch.getText();
        try {
            Product product = ProductModel.search(jobCardNo);
            if (product != null) {
                txtJobCardNo.setText(product.getJobCardNo());
                txtJobCardStartDate.setText(String.valueOf(product.getJobCardStartDate()));
                txtDescription.setText(product.getDescription());
                txtQty.setText(String.valueOf(product.getQty()));
                txtUnitPrice.setText(String.valueOf(product.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no Product found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String jobCardNo = txtJobCardNo.getText();
        String jobCardStartDate = txtJobCardStartDate.getText();
        String description= txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        var product = new Product(jobCardNo, jobCardStartDate, description, qty, unitPrice);
        if (Regex.validateProductCID(jobCardNo)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = ProductModel.update(product);
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! Product Updated!").show();
                        txtSearch.setText(null);
                        txtJobCardNo.setText(null);
                        txtJobCardStartDate.setText(null);
                        txtDescription.setText(null);
                        txtQty.setText(null);
                        txtUnitPrice.setText(null);
                        getAll();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "Product NOT UPDATE").show();
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
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/Final Project/Woodwork/src/main/java/lk/ijse/Woodwork/report/productReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
//            JasperPrintManager.printReport(jasperPrint, true);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    @FXML
    void setCellValueFactory() {
        colJobCardNo.setCellValueFactory(new PropertyValueFactory<>("jobCardNo"));
        colJobCardStartDate.setCellValueFactory(new PropertyValueFactory<>("jobCardStartDate"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    @FXML
    void getAll() {
        try {
            ObservableList<ProductTm> obList = FXCollections.observableArrayList();
            List<Product> cusList = ProductModel.getAll();

            for(Product product : cusList) {
                obList.add(new ProductTm(
                        product.getJobCardNo(),
                        product.getJobCardStartDate(),
                        product.getDescription(),
                        product.getQty(),
                        product.getUnitPrice()
                ));
            }
            tblProduct.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    @FXML
    void txtDescriptionOnAction(ActionEvent event) {
        txtQty.requestFocus();
    }

    @FXML
    void txtJobCardNoOnAction(ActionEvent event) {
        txtJobCardStartDate.requestFocus();
    }

    @FXML
    void txtJobCardStartDateOnAction(ActionEvent event) {
        txtDescription.requestFocus();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtUnitPrice.requestFocus();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        btnSearch.fire();
    }

    public void mouseClickGetDataOnAction(MouseEvent mouseEvent) {
        ProductTm selectedItem = tblProduct.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtJobCardNo.setText(selectedItem.getJobCardNo());
        txtJobCardStartDate.setText(selectedItem.getJobCardStartDate());
        txtDescription.setText(selectedItem.getDescription());
        txtQty.setText(String.valueOf(selectedItem.getQty()));
        txtUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
    }

}
