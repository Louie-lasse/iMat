package resources;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;

public class CartItemController extends AnchorPane {

    @FXML private ImageView ItemImage;
    @FXML private Label ItemNameLabel;
    @FXML private Label TotalPriceLabel;
    @FXML private Label PricePerPieceLabel;
    @FXML private TextField amount;
    private Product product;
    private static final BackendAdapter db = BackendAdapter.getInstance();
    private static Page parent;

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
        init();
        update();
    }

    public void init(){
        ItemImage.setImage(db.getFXImage(product));
        ItemNameLabel.setText(product.getName());
        TotalPriceLabel.setText(Double.toString(product.getPrice()));
        PricePerPieceLabel.setText(Unit.get(product).toString());
    }

    @FXML
    void add(MouseEvent event) {
        db.addProduct(product);
        update();
    }

    @FXML
    void subtract(MouseEvent event) {
        db.subtractProduct(product);
        update();
    }

    @FXML
    void remove() {
        db.removeProduct(product);
        update();
    }

    public static void setParent(Page page){
        parent = page;
    }

    public void update(){
        amount.setText(db.getFormattedAmount(product));
        parent.update();
    }

}
