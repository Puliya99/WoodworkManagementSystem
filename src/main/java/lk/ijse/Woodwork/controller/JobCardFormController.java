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
import lk.ijse.Woodwork.Model.JobCardModel;
import lk.ijse.Woodwork.Model.ProductModel;
import lk.ijse.Woodwork.db.DBConnection;
import lk.ijse.Woodwork.dto.Item;
import lk.ijse.Woodwork.dto.JobCardDTO;
import lk.ijse.Woodwork.dto.Product;
import lk.ijse.Woodwork.dto.tm.JobCardTm;
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

public class JobCardFormController implements Initializable {

    @FXML
    private AnchorPane addJobCard;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbJobCardNo;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colItemQty;

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
    private Label lblUnitPrice;

    @FXML
    private Label lblJobCardName;

    @FXML
    private Label lblIdCode;

    @FXML
    private Label lblIdDate;

    @FXML
    private TableView<JobCardTm> tblProductCart;

    @FXML
    private TextField txtQty;

    private ObservableList<JobCardTm> obList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadItemCodes();
        loadjobCardCodes();
        setCellValueFactory();
        setOrderDate();
        generateNextOrderId();
    }

    private void generateNextOrderId() {
        try {
            String nextId = JobCardModel.generateNextOrderId();
            lblIdCode.setText(nextId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        if (Integer.parseInt(txtQty.getText()) < Integer.parseInt(lblQtyOnHand.getText())) {
            String itemCode = cmbItemCode.getValue();
            String description = lblDescription.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());
            double total = qty * unitPrice;
            Button btnRemove = new Button("Remove");
            btnRemove.setCursor(Cursor.HAND);

            setRemoveBtnOnAction(btnRemove);

            if (!obList.isEmpty()) {
                for (int i = 0; i < tblProductCart.getItems().size(); i++) {
                    if (colItemCode.getCellData(i).equals(itemCode)) {
                        qty += (int) colItemQty.getCellData(i);
                        total = qty * unitPrice;

                        obList.get(i).setItemQty(qty);
                        obList.get(i).setTotal(total);

                        tblProductCart.refresh();
                        calculateNetTotal();
                        return;
                    }
                }
            }

            JobCardTm tm = new JobCardTm(itemCode, description, qty, unitPrice, total, btnRemove);

            obList.add(tm);
            tblProductCart.setItems(obList);
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

                int index = tblProductCart.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblProductCart.refresh();
                calculateNetTotal();
            }

        });
    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblProductCart.getItems().size(); i++) {
            double total  = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  addJobCard.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
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
    void btnReportOnAction(ActionEvent event) {
        try {
            JasperDesign design = JRXmlLoader.load(new File("/home/lmarcho/Documents/IJSE/Final Project/Woodwork/src/main/java/lk/ijse/Woodwork/report/JobCardReport.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String idCode = lblIdCode.getText();
        String jobCardNo = cmbJobCardNo.getValue();

        List<JobCardDTO> jobCardDTOList = new ArrayList<>();

        for (int i = 0; i < tblProductCart.getItems().size(); i++) {
            JobCardTm tm = obList.get(i);

            JobCardDTO jobCardDTO = new JobCardDTO(tm.getItemCode(), tm.getItemQty(), tm.getTotal());
            jobCardDTOList.add(jobCardDTO);
        }

        try {
            boolean isPlaced = JobCardModel.jobCard(idCode, jobCardNo, jobCardDTOList);
            if(isPlaced) {
                new Alert(Alert.AlertType.INFORMATION, "JobCard Placed!").show();
                generateNextOrderId();
            } else {
                new Alert(Alert.AlertType.ERROR, "JobCard Not Placed!").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setOrderDate() {
        lblIdDate.setText(String.valueOf(LocalDate.now()));
    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {
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

    @FXML
    void cmbJobCardNoOnAction(ActionEvent event) {
        String jobCardNo = (String) cmbJobCardNo.getSelectionModel().getSelectedItem();
        try {
            Product product = ProductModel.searchById(jobCardNo);
            lblJobCardName.setText(product.getDescription());
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loadjobCardCodes() {
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

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

}
