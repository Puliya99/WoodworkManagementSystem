package lk.ijse.Woodwork.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Woodwork.Model.CreateAccountModel;
import lk.ijse.Woodwork.util.Regex;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAccountFormController implements Initializable {

    @FXML
    private Button btnCreate;

    @FXML
    private AnchorPane root;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private TextField txtEmpId;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    @FXML
    private JFXButton btnShowMeConfirmPassword;

    @FXML
    private JFXButton btnShowMePassword;

    private Boolean passwordVisible;

    private Boolean confirmPasswordVisible;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmPasswordVisible = false;
        passwordVisible = false;
        btnShowMePassword.setOnAction(event -> togglePasswordVisibility());
        btnShowMeConfirmPassword.setOnAction(event -> toggleConfirmPasswordVisibility());
    }

    private void toggleConfirmPasswordVisibility() {
        passwordVisible = !passwordVisible;
        txtConfirmPassword.setManaged(false);
        txtConfirmPassword.setVisible(false);
        txtConfirmPassword.setManaged(true);
        txtConfirmPassword.setVisible(true);
        if (passwordVisible) {
            txtConfirmPassword.setPromptText(txtConfirmPassword.getText());
            txtConfirmPassword.setText("");
        } else {
            txtConfirmPassword.setText(txtConfirmPassword.getPromptText());
            txtConfirmPassword.setPromptText("");
        }
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        txtPassword.setManaged(false);
        txtPassword.setVisible(false);
        txtPassword.setManaged(true);
        txtPassword.setVisible(true);
        if (passwordVisible) {
            txtPassword.setPromptText(txtPassword.getText());
            txtPassword.setText("");
        } else {
            txtPassword.setText(txtPassword.getPromptText());
            txtPassword.setPromptText("");
        }
    }

    @FXML
    void btnCreateOnAction(ActionEvent event) {
        String empId = txtEmpId.getText();
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.WARNING, "Passwords do not match!").show();
            return;
        }

        if (Regex.validateEmployeeCID(empId) && Regex.validateUsername(username)&& Regex.validatePassword(password)) {
            try {
                boolean empIdVerified = CreateAccountModel.empIdVerified(empId);
                if (empIdVerified) {
                    boolean isSave = CreateAccountModel.save(username,password);
                    if (isSave) {
                        new Alert(Alert.AlertType.INFORMATION, "Account Create successful!").show();
                        Stage window = (Stage) root.getScene().getWindow();
                        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Oops something went wrong while updating password!").show();
                    }
                } else {
                    new Alert(Alert.AlertType.WARNING, "EmpId Not Verified!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Oops something wrong!").show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Hint : [aA-zZ0-9@]{8,20}").show();
        }
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Stage window = (Stage)  root.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
    }

    @FXML
    void txtConfirmPasswordOnAction(ActionEvent event) {
        btnCreate.fire();
    }

    @FXML
    void txtEmpIdOnAction(ActionEvent event) {
        txtEmpId.requestFocus();
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        txtConfirmPassword.requestFocus();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtUserName.requestFocus();
    }

}
