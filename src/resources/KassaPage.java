package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import se.chalmers.cse.dat216.project.Customer;

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
