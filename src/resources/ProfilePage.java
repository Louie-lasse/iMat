package resources;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Customer;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.*;


public class ProfilePage extends Page{
    private static final Customer customer = db.getCustomer();

    @FXML
    Button backButton;
    @FXML TextField name;
    @FXML TextField phoneNumber;
    @FXML TextField email;
    @FXML TextField address;
    @FXML TextField zip;
    @FXML TextField city;

    @FXML
    ImageView paymentImg;
    @FXML TextField cardNumber;
    @FXML TextField cardHolder;
    @FXML TextField month;
    @FXML TextField year;
    @FXML TextField cvc;
    @FXML Accordion prevBuyAcc;

    private static final HashMap<Order, TitledPane> orderTitledPaneHashMap = new HashMap<>();

    private static final HashMap<Order, PreviousBuyController> controllerHashMap = new HashMap<>();

    @Override
    protected void initialize() {


    }

    @Override
    protected FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    public void update() {
        name.setText(customer.getFirstName() + " " + customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        zip.setText(customer.getPostCode());
        city.setText(customer.getPostAddress());

        if(BackendAdapter.getCard().getCardType().equals("Mastercard")){
            paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                    "resources/images/mastercard.png")));
        }else{
            paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                    "resources/images/visa.png")));
        }

        cardHolder.setText(BackendAdapter.getCard().getHoldersName());
        cardNumber.setText(BackendAdapter.getCard().getCardNumber());
        month.setText(Integer.toString(BackendAdapter.getCard().getValidMonth()));
        year.setText(Integer.toString(BackendAdapter.getCard().getValidYear()));
        cvc.setText(Integer.toString(BackendAdapter.getCard().getVerificationCode()));

        updatePreviousPurchases();
    }

    private void updatePreviousPurchases(){
        List<Order> orders = db.getOrders();
        List<TitledPane> panes = prevBuyAcc.getPanes();
        panes.clear();
        for (Order order: orders){
            if(order.getItems().size() >= 1){
                panes.add(getTitledPane(order));
            }
        }
    }

    private TitledPane getTitledPane(Order order){
        TitledPane pane = orderTitledPaneHashMap.get(order);
        if (pane==null){
            pane = createPane(order);
            orderTitledPaneHashMap.put(order, pane);
        }
        return pane;
    }

    private TitledPane createPane(Order order){
        VBox box = new VBox();
        List<Node> items = box.getChildren();
        double price = 0;
        for (ShoppingItem shoppingItem : order.getItems()) {
            price += shoppingItem.getTotal();
            items.add(new OrderedItemController(shoppingItem));
        }
        String title = order.getDate().toString().substring(0, 10) + "      Totalt: " +  Math.round(price*100) / 100 + " kr";

        return new TitledPane(title, box);
    }

    @Override
    public void open(){
        update();
    }

    @FXML
    protected void close(ActionEvent event){
        parent.hidePopup();
    }

    @Override
    public void toFront(){
        super.toFront();
        open();
    }

    @FXML public void clear(){
        db.reset();
        parent.returnHome();
    }

}
