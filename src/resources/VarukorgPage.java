package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import javax.lang.model.element.VariableElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VarukorgPage extends Page{

    private static final HashMap<Product, CartItemController> controllerHashMap = new HashMap<>();

    @FXML
    private FlowPane varorFlowPane;

    @FXML
    private Label totalPrice;

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("varukorg.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    protected void initialize(){
        CartItemController.setParent(this);
        List<Product> products = db.getCartProducts();
        for (Product p: products){
            controllerHashMap.put(p, new CartItemController(p));
        }
    }

    @Override
    public void update() {
        totalPrice.setText("Totalt: " + (double) Math.round(db.getTotalPrice()*100) / 100 + " kr");
    }

    @Override
    public void open(){
        List<Product> products = db.getCartProducts();
        CartItemController cartItemController;
        List<CartItemController> controllers = new ArrayList<>();
        int temp = 0;
        for (Product p: products){
            cartItemController = controllerHashMap.get(p);
            if (cartItemController == null){
                cartItemController = new CartItemController(p);
                controllerHashMap.put(p, cartItemController);
            }
            if(temp % 2 == 0){
                cartItemController.setStyle("-fx-background-color: #ededed");
            }else{
                cartItemController.setStyle("-fx-background-color: white");
            }
            temp++;
            cartItemController.update();
            controllers.add(cartItemController);
        }
        List<Node> flowPaneChildren = varorFlowPane.getChildren();
        flowPaneChildren.clear();
        flowPaneChildren.addAll(controllers);
        update();
    }

    @Override
    public boolean isDone(){
        return db.getCartProducts().size() != 0;
    }

    @Override
    public void displayErrors(){
        totalPrice.setText("För att fortsätta måste du lägga till produkter i varukorgen");
    }
}
