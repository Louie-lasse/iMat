package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ShoppingGridItemController  extends AnchorPane {

    @FXML private Label nameLabel;

    @FXML private Label priceLabel;

    @FXML private ImageView productImage;

    private Product product;

    private Unit unit;

    private static final BackendAdapter db = BackendAdapter.getInstance();

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
        nameLabel.setText(product.getName());
        unit = Unit.get(product);
        priceLabel.setText(Double.toString(product.getPrice())+" "+unit);
        productImage.setImage(db.getFXImage(product));
    }

    @FXML
    protected void add(){
        //TODO vart ska det va?
        db.addProduct(this.product);
    }

    @FXML
    protected void subtract(){
        db.removeProduct(this.product);
    }

    private void update(){

    }

}
