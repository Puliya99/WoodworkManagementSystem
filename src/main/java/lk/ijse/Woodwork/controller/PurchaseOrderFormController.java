package lk.ijse.Woodwork.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Woodwork.Model.ItemModel;
import lk.ijse.Woodwork.Model.PurchaseOrderModel;
import lk.ijse.Woodwork.Model.SupplierModel;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Item;
import lk.ijse.Woodwork.dto.PurchaseOrderDto;
import lk.ijse.Woodwork.dto.Supplier;
import lk.ijse.Woodwork.dto.tm.PurchaseOrderTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class PurchaseOrderFormController implements Initializable {

    @FXML
    private AnchorPane addPlaceOrder;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblSuppliyDate;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<PurchaseOrderTm> tblOrderCart;

    @FXML
    private TextField txtQty;

    private ObservableList<PurchaseOrderTm> obList = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = (String) cmbItemCode.getValue();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
        double total = qty * unitPrice;
        Button btnRemove = new Button("Remove");
        btnRemove.setCursor(Cursor.HAND);

        setRemoveBtnOnAction(btnRemove);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTotal(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }
        }

        PurchaseOrderTm tm = new PurchaseOrderTm(code, description, qty, unitPrice, total, btnRemove);

        obList.add(tm);
        tblOrderCart.setItems(obList);
        calculateNetTotal();

    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addPlaceOrder.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSuppliyDate();
        loadSupplierIds();
        loadItemCodes();
        setCellValueFactory();

    }

    private void loadItemCodes() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.getCodes();

            for (String itemCode : codes) {
                obList.add(itemCode);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setSuppliyDate() {
        lblSuppliyDate.setText(String.valueOf(LocalDate.now()));
    }

    private void loadSupplierIds() {
        try {
            List<String> ids = SupplierModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String supplierId : ids) {
                obList.add(supplierId);
            }
            cmbSupplierId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblOrderCart.refresh();
                calculateNetTotal();
            }

        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnNewSupplierOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Supplier Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnNewItemOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/item_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Item Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

   @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String supplierId = cmbSupplierId.getValue();
        String itemCode = cmbItemCode.getValue();
        Integer suppliyQty = Integer.parseInt(txtQty.getText());
        Double suppliyeAmount = (Double.parseDouble(lblNetTotal.getText()));

       System.out.println(suppliyQty);

        List<PurchaseOrderDto> purchaseOrderList = new ArrayList<>();

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            PurchaseOrderTm tm = obList.get(i);

            PurchaseOrderDto purchaseOrder = new PurchaseOrderDto(tm.getCode(), tm.getQty(), tm.getUnitPrice(),Double.parseDouble(lblUnitPrice.getText()));
            purchaseOrderList.add(purchaseOrder);
        }

        try {
            boolean isPlaced = PurchaseOrderModel.purchaseOrder( itemCode,supplierId,suppliyQty,suppliyeAmount, purchaseOrderList);
            if(isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {
        String supplierId = (String) cmbSupplierId.getSelectionModel().getSelectedItem();
        try {
            Supplier supplier = SupplierModel.searchById(supplierId);
            lblSupplierName.setText(supplier.getSupplierName());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

   @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = (String) cmbItemCode.getSelectionModel().getSelectedItem();
        try {
            Item item = ItemModel.searchById(code);
            fillItemFields(item);
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillItemFields(Item item) {
        lblDescription.setText(item.getDescription());
        lblQtyOnHand.setText(String.valueOf(item.getQty()));
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/Final Project/Woodwork/src/main/java/lk/ijse/Woodwork/report/purchaseOrder.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

}
