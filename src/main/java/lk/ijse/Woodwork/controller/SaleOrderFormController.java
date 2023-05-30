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
import lk.ijse.Woodwork.Model.CustomerModel;
import lk.ijse.Woodwork.Model.ProductModel;
import lk.ijse.Woodwork.Model.SaleOrderModel;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Customer;
import lk.ijse.Woodwork.dto.Product;
import lk.ijse.Woodwork.dto.SaleOrderDto;
import lk.ijse.Woodwork.dto.tm.SaleOrderTm;
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

public class SaleOrderFormController implements Initializable {

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbJobCardNo;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colJobCardNo;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colProductQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitProductPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblJobCardDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private TableView<SaleOrderTm> tblSaleOrderCart;

    @FXML
    private TextField txtQty;

    @FXML
    private AnchorPane pane;
    private ObservableList<SaleOrderTm> obList = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        if (Integer.parseInt(txtQty.getText()) < Integer.parseInt(lblQtyOnHand.getText())) {
            String orderId = lblOrderId.getText();
            String jobCardCode = cmbJobCardNo.getValue();
            String description = lblDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double total = qty * unitPrice;
            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);

            setRemoveBtnOnAction(btnRemove);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblSaleOrderCart.getItems().size(); i++) {
                    if (colOrderId.getCellData(i).equals(orderId)) {
                        qty += (int) colProductQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setProductQty(qty);
                        obList.get(i).setTotal(total);

                        tblSaleOrderCart.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }
            SaleOrderTm tm = new SaleOrderTm(orderId, jobCardCode, description, qty, unitPrice, total, btnRemove);

            obList.add(tm);
            tblSaleOrderCart.setItems(obList);
            calculateNetTotal();
        }else {
            new Alert(Alert.AlertType.WARNING,"Out of Stoke").show();
        }

    }
    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblSaleOrderCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblSaleOrderCart.refresh();
                calculateNetTotal();
            }

        });
    }
    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblSaleOrderCart.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customer_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnNewProductOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/product_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = new Stage();
        stage.setTitle("Product Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/Final Project/Woodwork/src/main/java/lk/ijse/Woodwork/report/SaleOrderReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String jobCardNo = cmbJobCardNo.getValue();
        String Description = lblDescription.getText();
        Integer productQty = Integer.parseInt(txtQty.getText());
        Double productUnitPrice = (Double.parseDouble(lblNetTotal.getText()));
        String customerId = cmbCustomerId.getSelectionModel().getSelectedItem();
        String amount = lblNetTotal.getText();

        List<SaleOrderDto> saleOrderList = new ArrayList<>();

        for (int i = 0; i < tblSaleOrderCart.getItems().size(); i++) {
            SaleOrderTm tm = obList.get(i);

            SaleOrderDto saleOrder = new SaleOrderDto(tm.getJobCardNumber(), tm.getProductQty(), tm.getUnitProductPrice(),Double.parseDouble(lblUnitPrice.getText()));
            saleOrderList.add(saleOrder);
        }

        try {
            boolean isPlaced = SaleOrderModel.saleOrder(orderId,jobCardNo,Description,productQty,productUnitPrice, saleOrderList,customerId,LocalDate.now(),amount);
            if(isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "Order Placed!").show();
                generateNextOrderId();

            } else {
                new Alert(Alert.AlertType.ERROR, "Order Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void cmbCustomerIdOnAction(ActionEvent event) {
        String cus_id = cmbCustomerId.getSelectionModel().getSelectedItem();
        try {
            Customer customer = CustomerModel.searchById(cus_id);
            lblCustomerName.setText(customer.getCustName());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void cmbJobCardNoOnAction(ActionEvent event) {
        String code = cmbJobCardNo.getSelectionModel().getSelectedItem();

        try {
            Product product = ProductModel.searchById(code);
            fillItemFields(product);
            txtQty.requestFocus();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        setOrderDate();
        loadCustomerIds();
        loadItemCodes();
        generateNextOrderId();
    }

    private void generateNextOrderId() {
        try {
            String nextId = SaleOrderModel.generateNextOrderId();
            lblOrderId.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemCodes() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> jobCardNo = ProductModel.getCodes();

            for (String code : jobCardNo ) {
                obList.add(code);
            }
            cmbJobCardNo.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadCustomerIds() {
        try {
            List<String> ids = CustomerModel.getIds();
            ObservableList<String> obList = FXCollections.observableArrayList();

            for (String id : ids) {
                obList.add(id);
            }
            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colJobCardNo.setCellValueFactory(new PropertyValueFactory<>("jobCardNumber"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colProductQty.setCellValueFactory(new PropertyValueFactory<>("productQty"));
        colUnitProductPrice.setCellValueFactory(new PropertyValueFactory<>("unitProductPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void fillItemFields(Product product) {
        lblDescription.setText(product.getDescription());
        lblJobCardDate.setText(String.valueOf(product.getJobCardStartDate()));
        lblUnitPrice.setText(String.valueOf(product.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(product.getQty()));
    }
}
