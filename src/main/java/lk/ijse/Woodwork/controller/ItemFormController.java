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
import lk.ijse.Woodwork.bo.custom.ItemBo;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.ItemDTO;
import lk.ijse.Woodwork.dto.tm.ItemTm;
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

public class ItemFormController implements Initializable {

    @FXML
    private AnchorPane addItem;

    @FXML
    private Button btnSearch;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtUnitPrice;

    ItemBo itemBo = (ItemBo) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addItem.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtSearch.setText(null);
        txtItemCode.setText(null);
        txtDescription.setText(null);
        txtQty.setText(null);
        txtUnitPrice.setText(null);
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String ID = txtItemCode.getText();
        try {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Delete?", yes, no).showAndWait();
            if (result.orElse(no) == yes) {
                Boolean isDeleted = itemBo.deleteItem(ID);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Item DELETED").show();
                    txtSearch.setText(null);
                    txtItemCode.setText(null);
                    txtDescription.setText(null);
                    txtQty.setText(null);
                    txtUnitPrice.setText(null);
                    getAll();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Item NOT DELETED").show();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Sql error").show();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        if (Regex.validateItemCID(itemCode)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Save?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isSaved = itemBo.saveItem(new ItemDTO(itemCode, description, qty, unitPrice));
                    if (isSaved) {
                        new Alert(Alert.AlertType.INFORMATION, "Item saved!!!").show();
                        txtSearch.setText(null);
                        txtItemCode.setText(null);
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
        String itemId = txtSearch.getText();
        try {
            ItemDTO item = itemBo.searchItem(itemId);
            if (item != null) {
                txtItemCode.setText(item.getItemCode());
                txtDescription.setText(item.getDescription());
                txtQty.setText(String.valueOf(item.getQty()));
                txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
            } else {
                new Alert(Alert.AlertType.WARNING, "no item found :(").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "oops! something went wrong :(").show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        String itemCode = txtItemCode.getText();
        String description = txtDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());

        var item = new ItemDTO(itemCode, description, qty, unitPrice);
        if (Regex.validateItemCID(itemCode)) {
            try {
                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to Update?", yes, no).showAndWait();
                if (result.orElse(no) == yes) {
                    boolean isUpdated = itemBo.updateItem(new ItemDTO(itemCode, description, qty, unitPrice));
                    if (isUpdated) {
                        new Alert(Alert.AlertType.INFORMATION, "huree! Item Updated!").show();
                        txtSearch.setText(null);
                        txtItemCode.setText(null);
                        txtDescription.setText(null);
                        txtQty.setText(null);
                        txtUnitPrice.setText(null);
                        getAll();
                    } else {
                        new Alert(Alert.AlertType.WARNING, "ITEM NOT UPDATE").show();
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

    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    @FXML
    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
    }

    @FXML
    void getAll() {
        try {
            ObservableList<ItemTm> obList = FXCollections.observableArrayList();
            List<ItemDTO> cusList = itemBo.getAllItem();

            for(ItemDTO item : cusList) {
                obList.add(new ItemTm(
                        item.getItemCode(),
                        item.getDescription(),
                        item.getQty(),
                        item.getUnitPrice()
                ));
            }
            tblItem.setItems(obList);
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
    void txtItemCodeOnAction(ActionEvent event) {
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
        ItemTm selectedItem = tblItem.getSelectionModel().getSelectedItem();
        if(selectedItem==null)return;
        txtItemCode.setText(selectedItem.getItemCode());
        txtDescription.setText(selectedItem.getDescription());
        txtQty.setText(String.valueOf(selectedItem.getQty()));
        txtUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/2nd Semester/woodWork Project convert to Layeard/Woodwork/src/main/java/lk/ijse/Woodwork/report/ItemReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

}
