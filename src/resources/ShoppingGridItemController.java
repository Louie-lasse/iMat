package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ShoppingGridItemController  extends AnchorPane {

    @FXML private Label NameLabel;

    @FXML private Label PriceLabel;

    @FXML private ImageView ProductImage;

    private Product product;

    private Unit unit;


    public ShoppingGridItemController(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingGridItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        //Anropa init

        this.product=product;

        initialize();
        update();
    }

    private void initialize(){
        // Kan behövas för att synliggöra rundade hörn// scene.setFill(Color.TRANSPARENT); //Makes the back-background of the cards transparent
        // Kan behövas för att synliggöra rundade hörn// stage.initStyle(StageStyle.TRANSPARENT); //Makes the back-background of the cards transparent
        NameLabel.setText(product.getName());
        unit = Unit.get(product);
        PriceLabel.setText(Double.toString(product.getPrice())+unit);
    }

    private void update(){

    }

}
