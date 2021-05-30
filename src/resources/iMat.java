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

        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"), bundle);

        Scene scene = new Scene(root);


        //stage.setFullScreen(true);
        //stage.setMaximized(true);
        stage.setResizable(false);
        stage.setTitle(bundle.getString("application.name"));
        stage.setScene(scene);
        stage.getIcons().add(new Image("/resources/images/iMat_square_icon.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                BackendAdapter.getInstance().shutDown();
            }
        }));
    }
}
