package resources;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class iMat extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle bundle = java.util.ResourceBundle.getBundle("resources/iMat");

        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"), bundle);

        Scene scene = new Scene(root);

        stage.setFullScreen(true);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/images/iMat_square_icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
