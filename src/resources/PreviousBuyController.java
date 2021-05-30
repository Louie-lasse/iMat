package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class PreviousBuyController extends AnchorPane {
    private Order order;

    @FXML Label dateLabel;
    @FXML Label priceLabel;
    @FXML
    ImageView showDetails;
    private static Page parent;

    public PreviousBuyController(Order order){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PreviousBuy.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.order = order;
        init();
        update();
    }
    public void init(){
        dateLabel.setText(this.order.getDate().toString().substring(0,10));
        int total = 0;
        for(ShoppingItem items : this.order.getItems()){
            total += items.getTotal();
        }
        priceLabel.setText(Double.toString(total));
    }
    public void update(){
        //parent.update();
    }
}
