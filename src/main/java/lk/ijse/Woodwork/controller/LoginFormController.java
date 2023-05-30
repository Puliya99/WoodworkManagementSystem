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
import lk.ijse.Woodwork.Model.LoginModel;
import lk.ijse.Woodwork.util.Regex;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {


    @FXML
    private Button btnLogin;

    @FXML
    private JFXButton btnShowPassword;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane rootChange;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    private Boolean passwordVisible;

    @FXML
    void btnLoginOnAction(ActionEvent actionEvent) {
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        if (Regex.validateUsername(username)&& Regex.validatePassword(password)) {
            try {
                boolean isUserVerified = LoginModel.userVerified(username, password);
                if (isUserVerified) {
                    Stage window = (Stage) root.getScene().getWindow();
                    window.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"))));
                } else {
                    new Alert(Alert.AlertType.WARNING, "User Not Verified!!!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Oops something wrong!!!").show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            new Alert(Alert.AlertType.INFORMATION, "Hint : [aA-zZ0-9@]{8,20}").show();
        }
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        passwordVisible = false;
        btnShowPassword.setOnAction(event -> togglePasswordVisibility());
    }

    @FXML
    void txtPasswordOnAction(ActionEvent event) {
        btnLogin.fire();
    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {
        txtPassword.requestFocus();
    }

    @FXML
    void btnForogtPasswordOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/forgot_password_form.fxml")));
    }

    @FXML
    void btnCreateAccountOnAction(ActionEvent event) throws IOException {
        rootChange.getChildren().clear();
        rootChange.getChildren().add(FXMLLoader.load(getClass().getResource("/view/create_account_form.fxml")));
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

}
