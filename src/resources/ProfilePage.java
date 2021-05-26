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
    @FXML Label name;
    @FXML Label phoneNumber;
    @FXML Label email;
    @FXML Label address;
    @FXML Label zip;
    @FXML Label city;

    @FXML
    ImageView paymentImg;
    @FXML TextField cardNumber;
    @FXML TextField cardHolder;
    @FXML TextField month;
    @FXML TextField year;
    @FXML TextField cvc;
    @FXML Label orderNumber;
    @FXML Accordion prevBuyAcc;

    private static final HashMap<Order, TitledPane> orderTitledPaneHashMap = new HashMap<>();

    private static final HashMap<Order, PreviousBuyController> controllerHashMap = new HashMap<>();

    @Override
    protected void initialize() {
        /*
        List<Order> orders = BackendAdapter.getInstance().getOrders();
        for (Order order : orders){
            if(order.getItems().size() > 0){
                controllerHashMap.put(order, new PreviousBuyController(order));
            }
        }
        prevBuyAcc.getPanes().clear();
        for(Order order : BackendAdapter.getInstance().getOrders()){
            VBox contents = getItems(order); //går ej returnerna flowpane?
            String datum = order.getDate().toString().substring(0, 10);
            int total = 0;
            for(ShoppingItem items : order.getItems()){
                total += items.getTotal();
            }
            String name = datum + "        " + total + " kr       " + " Visa köpdetaljer";

            TitledPane previousOrder = new TitledPane(name, contents);
            prevBuyAcc.getPanes().add(previousOrder);
        }
        updatePreviousPurchases();

         */
    }
    /*
    public VBox getItems(Order order){
        FlowPane flowPane = new FlowPane();
        for(ShoppingItem items : order.getItems()){
            flowPane.getChildren().add(items); //Går ej adda ShoppingListItem?
        }
        flowPane.getChildren().add(new Button("TEST"));
        VBox box = new VBox();
        box.getChildren().add(flowPane);
        return box;
    }
    */
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
    //    prevBuys();
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
    /*
    public void prevBuys(){
        List<Order> orders = BackendAdapter.getInstance().getOrders();
        PreviousBuyController previousBuyController;
        List<PreviousBuyController> controllers = new ArrayList<>();

        for(Order order : orders){
            previousBuyController = controllerHashMap.get(order);
            if(previousBuyController == null){
                previousBuyController = new PreviousBuyController(order);
                controllerHashMap.put(order, previousBuyController);
            }
            controllers.add(previousBuyController);
        }
        List<TitledPane> flowPaneChildren = prevBuyAcc.getPanes();
        flowPaneChildren.clear();
        flowPaneChildren.addAll(controllers);
    }

     */


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

}
