package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Customer;

import javax.swing.event.DocumentListener;
import java.io.IOException;

public class KassaPage extends Page {

    private static final Customer customer = db.getCustomer();


    @FXML Text name;
    @FXML Text phoneNumber;
    @FXML Text email;
    @FXML Text address;
    @FXML Text date;
    @FXML Text time;
    @FXML TextArea message;

    ToggleGroup toggleGroup;
    @FXML RadioButton card;
    @FXML RadioButton klarna;
    @FXML RadioButton bill;
    @FXML Pane cardPane;
    @FXML Pane klarnaPane;
    @FXML Pane billPane;

    @FXML ImageView paymentImg;
    @FXML TextField cardNumber;
    @FXML TextField month;
    @FXML TextField year;
    @FXML TextField cardHolder;
    @FXML TextField cvc;


    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kassa.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }

    @Override
    protected void initialize() {
        name.setText(customer.getFirstName() + " " + customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress() + ", " + customer.getPostCode() + ", " + customer.getPostAddress());

        toggleGroup = new ToggleGroup();
        card.setToggleGroup(toggleGroup);
        klarna.setToggleGroup(toggleGroup);
        bill.setToggleGroup(toggleGroup);
        card.setSelected(true);
        cardPane.toFront();

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (toggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
                    setPayment(((RadioButton) toggleGroup.getSelectedToggle()).getId());
                }
            }
        });

        cvc.textProperty().addListener((observable, oldValue, newValue) -> {
            cvc.setText(maxInput(cvc.getText(), 3));
        });

        month.textProperty().addListener((observable, oldValue, newValue) -> {
            month.setText(maxInput(month.getText(), 2));
        });
        year.textProperty().addListener((observableValue, oldValue, newValue) -> {
            year.setText(maxInput(year.getText(), 2));
        });
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if(cardNumber.getText().length() != 0) {
                if(cardNumber.getText().charAt(0) == '4'){
                    paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                            "resources/images/mastercard.png")));
                }else{
                    paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                            "resources/images/visa.png")));
                }
                cardNumber.setText(maxInput(cardNumber.getText(), 16));
            }
        });
    }
    public String maxInput(String input, int maxLength){
        if(input.length() > maxLength){
            return input.substring(0, maxLength);
        }
        return input;
    }
    private void setPayment(String type){
        switch (type){
            case "card":
                System.out.println("Card");
                cardPane.toFront();
                break;
            case "klarna":
                System.out.println("Klarna");
                klarnaPane.toFront();
                break;
            case "bill":
                System.out.println("Faktura");
                billPane.toFront();
                break;
            default:
                System.out.println("Error Error Error");
                break;
        }
    }

    @Override
    public void update() {

    }

    @Override
    public void open(){
        update();
    }
}
