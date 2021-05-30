package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class OrderedItemController extends AnchorPane {

    private final static BackendAdapter db = BackendAdapter.getInstance();

    @FXML
    private ImageView image;

    @FXML
    private Label name;

    @FXML
    private Label unit;

    @FXML
    private Label price;

    OrderedItemController(ShoppingItem item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderedItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init(item);
    }

    private void init(ShoppingItem item){
        image.setImage(db.getFXImage(item.getProduct()));
        name.setText(item.getProduct().getName() +": " + item.getProduct().getPrice() +Unit.get(item.getProduct()));
        price.setText((double)Math.round(item.getTotal()*100)/100 + " kr");
    }
}
