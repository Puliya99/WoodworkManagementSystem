package lk.ijse.Woodwork;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/view/login_form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Woodwork Book");
        Image image = new Image(getClass().getResourceAsStream("/img/logo.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
