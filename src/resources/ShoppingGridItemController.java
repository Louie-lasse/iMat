package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.chalmers.cse.dat216.project.Product;

public class ShoppingGridItemController {

    @FXML private Label NameLabel;
    @FXML private Label PriceLabel;
    @FXML private ImageView ProductImage;
    private Product product;


    public ShoppingGridItemController(Product product){

        //Anropa init

        this.product=product;
        NameLabel.setText(product.getName());
        PriceLabel.setText(Double.toString(product.getPrice()));


    }

    private void init(Stage stage) throws Exception {

        // Kan behövas för att synliggöra rundade hörn// scene.setFill(Color.TRANSPARENT); //Makes the back-background of the cards transparent
        // Kan behövas för att synliggöra rundade hörn// stage.initStyle(StageStyle.TRANSPARENT); //Makes the back-background of the cards transparent
        stage.show();

    }

}
