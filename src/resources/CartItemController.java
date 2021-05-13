package resources;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Product;

public class CartItemController {

    @FXML private ImageView ItemImage;
    @FXML private Label ItemNameLabel;
    @FXML private Label TotalPriceLabel;
    @FXML private Label PricePerPieceLabel;
    private double amount;
    private Product product;

    public CartItemController(Product product){

    }



}
