package lk.ijse.Woodwork.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Woodwork.Model.*;
import lk.ijse.Woodwork.dto.*;
import lombok.SneakyThrows;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private AnchorPane rootChange;

    @FXML
    private BarChart<String ,Integer> barChartEmpCount;

    public static String comDate;

    @FXML
    void btnAttendanceOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/attendance_book_form.fxml")));
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  rootChange.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/employee_form.fxml")));
    }

    @FXML
    void btnItemsOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/item_form.fxml")));
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  rootChange.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
    }

    @FXML
    void btnOrdersOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Purchase Order", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Sale Order", ButtonBar.ButtonData.OK_DONE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "SELECT ORDER", yes, no).showAndWait();
        if (result.orElse(no) == yes) {
            rootChange.getChildren().clear();
            rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/purchase_order_form.fxml")));
        }if (result.orElse(yes) == no){
            rootChange.getChildren().clear();
            rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/sale_order_form.fxml")));
        }
    }

    @FXML
    void btnProductOnAction(ActionEvent event) throws IOException {
        ButtonType yes = new ButtonType("Add Product", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Add Job Card", ButtonBar.ButtonData.OK_DONE);
        Optional<ButtonType> result = new Alert(Alert.AlertType.CONFIRMATION, "SELECT", yes, no).showAndWait();
        if (result.orElse(no) == yes) {
            rootChange.getChildren().clear();
            rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/product_form.fxml")));
        }if (result.orElse(yes) == no){
            rootChange.getChildren().clear();
            rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/jobCard_form.fxml")));
        }
    }

    @FXML
    void btnSupplierOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml")));
    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/customer_form.fxml")));
    }
    private void initClock() {
        Duration Duration = null;
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            lblTime.setText(LocalDateTime.now().format(formatter));

            SimpleDateFormat formatter2 = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            lblDate.setText(formatter2.format(date));
            comDate = lblDate.getText();
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @SneakyThrows
    @Override

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();
        loadTypeChart();
    }

    private void loadTypeChart() throws SQLException {
        XYChart.Series<String, Integer>[] series1 = new XYChart.Series[5];

        series1[0] = new XYChart.Series<>();
        String type = "Employee";
        series1[0].getData().add(new XYChart.Data<>("", setEmployeeCount()));
        series1[0].setName(type);

        series1[1] = new XYChart.Series<>();
        String type1 = "Supplier";
        series1[1].getData().add(new XYChart.Data<>("", setSupplierCount()));
        series1[1].setName(type1);

        series1[2] = new XYChart.Series<>();
        String type2 = "Customer";
        series1[2].getData().add(new XYChart.Data<>("", setCustomerCount()));
        series1[2].setName(type2);

        series1[3] = new XYChart.Series<>();
        String type3 = "Item";
        series1[3].getData().add(new XYChart.Data<>("", setItemCount()));
        series1[3].setName(type3);

        series1[4] = new XYChart.Series<>();
        String type4 = "Product";
        series1[4].getData().add(new XYChart.Data<>("", setProductCount()));
        series1[4].setName(type4);

        barChartEmpCount.getData().addAll(series1);
    }

    public int setEmployeeCount() throws SQLException {
        List<Employee> empList = EmployeeModel.getAll();
        return empList.size();
    }
    public int setSupplierCount() throws SQLException {
        List<Supplier> empList = SupplierModel.getAll();
        return empList.size();
    }
    public int setCustomerCount() throws SQLException {
        List<Customer> empList = CustomerModel.getAll();
        return empList.size();
    }
    public int setItemCount() throws SQLException {
        List<Item> empList = ItemModel.getAll();
        return empList.size();
    }
    public int setProductCount() throws SQLException {
        List<Product> empList = ProductModel.getAll();
        return empList.size();
    }

}


