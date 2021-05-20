package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class ShoppingGridItemController  extends AnchorPane {

    @FXML private Label nameLabel;

    @FXML private Label priceLabel;

    @FXML private ImageView productImage;

    @FXML private TextField amount;

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
        db.addProduct(this.product);
        update();
    }

    @FXML
    protected void subtract(){
        db.subtractProduct(this.product);
        update();
    }


    @FXML
    void remove(ActionEvent event) {
        db.removeProduct(this.product);
        update();
    }

    public void update(){
        amount.setText(db.getFormattedAmount(product));
    }

}
