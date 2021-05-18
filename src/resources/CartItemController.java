package resources;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class CartItemController extends AnchorPane {

    @FXML private ImageView ItemImage;
    @FXML private Label ItemNameLabel;
    @FXML private Label TotalPriceLabel;
    @FXML private Label PricePerPieceLabel;
    private double amount;
    private Product product;
    private static final BackendAdapter db = BackendAdapter.getInstance();

    public CartItemController(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        initialize();
        update();
    }

    public void initialize(){
        ItemImage.setImage(db.getFXImage(product));
        ItemNameLabel.setText(product.getName());
        TotalPriceLabel.setText(Double.toString(product.getPrice()));
        PricePerPieceLabel.setText(Unit.get(product).toString());
    }

    public void update(){

    }

}
