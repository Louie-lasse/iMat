package resources;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.text.Font;
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
    @FXML TextField firstName;
    @FXML TextField lastName;
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
    @FXML Button saveButton;

    @FXML AnchorPane clearPopUp;
    @FXML ScrollPane profilePage;

    private static final HashMap<Order, TitledPane> orderTitledPaneHashMap = new HashMap<>();

    private static final HashMap<Order, PreviousBuyController> controllerHashMap = new HashMap<>();

    @Override
    protected void initialize() {
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                saveUserInfo();
            }
        });
        cardHolder.textProperty().addListener((observable, oldValue, newValue) -> {
            cardHolder.setText(lettersOnly(cardHolder.getText()));
        });
        cvc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (cvc.getText().length() > 3) {
                String max = cvc.getText().substring(0, 3);
                cvc.setText(max);
            }
            cvc.setText(onlyNumbers(cvc.getText()));
        });

        month.textProperty().addListener((observable, oldValue, newValue) -> {
            month.setText(maxLength(month.getText(), 2));
            month.setText(onlyNumbers(month.getText()));

            if (month.getText().length() == 2) {
                changeField(month.getText(), 2, year);
            }
        });
        year.textProperty().addListener((observableValue, oldValue, newValue) -> {
            year.setText(maxLength(year.getText(), 2));
            year.setText(onlyNumbers(year.getText()));

            if (year.getText().length() == 2) {
                changeField(year.getText(), 2, cardHolder);
            }
        });
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < oldValue.length()) {
                cardNumber.setText(newValue);
            } else {
                if (cardNumber.getText().length() != 0) {
                    if (!cardNumber.getText().matches("\\d *")) {
                        cardNumber.setText(cardNumber.getText().replaceAll("[^\\d ]", ""));
                    }
                    if (cardNumber.getText().charAt(0) == '5') {
                        paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                                "resources/images/mastercard.png")));
                    } else {
                        paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                                "resources/images/visa.png")));
                    }
                    cardNumber.setText(maxLength(cardNumber.getText(), 20));
                    if (cardNumber.getText().length() == 20) {
                        changeField(cardNumber.getText(), 20, month);
                    }
                    if (cardNumber.getText().length() == 4) {
                        cardNumber.setText(cardNumber.getText() + " ");
                    } else if (cardNumber.getText().length() > 4 && cardNumber.getText().length() % 5 == 0
                            && cardNumber.getText().length() < 20) {
                        cardNumber.setText(cardNumber.getText() + " ");
                    }
                }
            }
        });
    }

    @Override
    protected FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }
    private String maxLength(String input, int maxLength){
        if(input.length() > maxLength){
            String temp = input.substring(0, maxLength);
            return temp;
        }
        return input;
    }
    private void changeField(String input, int maxLength, TextField next){
        if(input.length() == maxLength){
            next.requestFocus();
        }
    }
    private String onlyNumbers(String input){
        if (!input.matches("\\d*")) {
            return (input.replaceAll("[^\\d]", ""));
        }
        return input;
    }
    private String lettersOnly(String input){
        if (!input.matches("\\sa-öA-Ö *")) {
            return (input.replaceAll("[^\\sa-öA-Ö ]", ""));
        }
        //TODO if we run through the string with "if (!input.matches[...])", we might as well just do
        // return (input.replaceAll("[^\\sa-öA-Ö ]", ""));
        return input;
    }


    @Override
    public void update() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
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
        cvc.setText(getCVC());

        updatePreviousPurchases();
    }
    public void saveUserInfo(){
        BackendAdapter.getCard().setCardNumber(cardNumber.getText());
        if(cardNumber.getText().charAt(0) == '5'){
            BackendAdapter.getCard().setCardType("Mastercard");
        }else{
            BackendAdapter.getCard().setCardType("Visa");
        }
        BackendAdapter.getCard().setHoldersName(cardHolder.getText());
        BackendAdapter.getCard().setValidYear(Integer.parseInt(year.getText()));
        BackendAdapter.getCard().setValidMonth(Integer.parseInt(month.getText()));
        BackendAdapter.getCard().setVerificationCode(Integer.parseInt(cvc.getText()));

        customer.setFirstName(firstName.getText());
        customer.setLastName(lastName.getText());
        customer.setPhoneNumber(phoneNumber.getText());
        customer.setEmail(email.getText());
        customer.setAddress(address.getText());
        customer.setPostCode(zip.getText());
        customer.setPostAddress(city.getText());

    }
    private String getCVC(){
        StringBuilder sb = new StringBuilder(Integer.toString(BackendAdapter.getCard().getVerificationCode()));
        if (sb.length() >= 3) return sb.toString();
        while (sb.length() < 3) {
            sb.insert(0, 0);
        }
        return sb.toString();
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
        profilePage.toFront();
    }

    @FXML
    protected void close(ActionEvent event){
        parent.hidePopup();
    }


    @FXML public void clear(){
        clearPopUp.toFront();

    }


    @FXML public void confirm(){
        db.reset();
        parent.returnHome();
    }

    @FXML public  void cancel(){
        profilePage.toFront();
    }



}
