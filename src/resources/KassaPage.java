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
    @FXML Button payButton;
    @FXML ImageView paymentImg;
    @FXML TextField month;
    @FXML TextField year;
    @FXML TextField cardHolder;
    @FXML TextField cvc;
    @FXML TextField cardNumber;
    @FXML ImageView cardNumberDone;
    @FXML ImageView cardDateDone;
    @FXML ImageView cardHolderDone;
    @FXML ImageView cvcDone;
    @FXML ImageView cardNumberError;
    @FXML ImageView cardDateError;
    @FXML ImageView cardHolderError;
    @FXML ImageView cvcError;

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kassa.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }

    public Button getPayButton() {
        return payButton;
    }

    @Override
    protected void initialize() {
        toggleGroup = new ToggleGroup();
        card.setToggleGroup(toggleGroup);
        klarna.setToggleGroup(toggleGroup);
        bill.setToggleGroup(toggleGroup);
        card.setSelected(true);
        cardPane.toFront();

        cardNumberDone.setVisible(false);
        cardDateDone.setVisible(false);
        cvcDone.setVisible(false);
        cardHolderDone.setVisible(false);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

                if (toggleGroup.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) toggleGroup.getSelectedToggle();
                    setPayment(((RadioButton) toggleGroup.getSelectedToggle()).getId());
                }
            }
        });
        cardHolder.textProperty().addListener((observable, oldValue, newValue) -> {
            cardHolder.setText(lettersOnly(cardHolder.getText()));
        });

        cvc.textProperty().addListener((observable, oldValue, newValue) -> {
            cardHolderDone.setVisible(true);
            cardHolderError.setVisible(false);
            if(cvc.getText().length() > 3){
                String max = cvc.getText().substring(0, 3);
                cvc.setText(max);
            }
            if(cvc.getText().length() > 2){
                cvcDone.setVisible(true);
                cvcError.setVisible(false);
            }else{
                cvcDone.setVisible(false);
                cvcError.setVisible(true);

            }
            cvc.setText(onlyNumbers(cvc.getText()));
        });

        month.textProperty().addListener((observable, oldValue, newValue) -> {
            month.setText(maxLength(month.getText(), 2));
            month.setText(onlyNumbers(month.getText()));

            if(month.getText().length() == 2){
                changeField(month.getText(), 2, year);
                checkMonthDate();
            }
        });
        year.textProperty().addListener((observableValue, oldValue, newValue) -> {
            year.setText(maxLength(year.getText(), 2));
            year.setText(onlyNumbers(year.getText()));

            if(year.getText().length() == 2){
                changeField(year.getText(), 2, cardHolder);
            }
            checkMonthDate();
        });
        cardNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            if(cardNumber.getText().length() != 0) {
                if (!cardNumber.getText().matches("\\d *")) {
                    cardNumber.setText(cardNumber.getText().replaceAll("[^\\d ]", ""));
                }
                if(cardNumber.getText().charAt(0) == '4'){
                    paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                            "resources/images/mastercard.png")));
                }else{
                    paymentImg.setImage(new Image(getClass().getClassLoader().getResourceAsStream(
                            "resources/images/visa.png")));
                }
                cardNumber.setText(maxLength(cardNumber.getText(), 20));
                if(cardNumber.getText().length() == 20){
                    changeField(cardNumber.getText(), 20, month);
                    cardNumberDone.setVisible(true);
                    cardNumberError.setVisible(false);
                }else{
                    cardNumberDone.setVisible(false);
                    cardNumberError.setVisible(true);
                }
                if(cardNumber.getText().length() == 4){
                    cardNumber.setText(cardNumber.getText() + " ");
                }else if(cardNumber.getText().length() > 4 && cardNumber.getText().length()%5 == 0
                        && cardNumber.getText().length() < 20){
                    cardNumber.setText(cardNumber.getText() + " ");
                }
            }
        });
    }
    public void checkMonthDate() {
        if(!year.getText().isEmpty() && !month.getText().isEmpty()){
            if (Integer.parseInt(year.getText()) == 21 && Integer.parseInt(month.getText()) > 5
                    && Integer.parseInt(month.getText()) < 13) {
                cardDateDone.setVisible(true);
                cardDateError.setVisible(false);
            } else if (Integer.parseInt(month.getText()) > 0 && Integer.parseInt(month.getText()) < 13 &&
                    Integer.parseInt(year.getText()) > 21){
                cardDateDone.setVisible(true);
                cardDateError.setVisible(false);
            }else{
                cardDateDone.setVisible(false);
                cardDateError.setVisible(true);
            }
        }
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

    private String lettersOnly(String input){
        if (!input.matches("\\sa-öA-Ö *")) {
            return (input.replaceAll("[^\\sa-öA-Ö ]", ""));
        }
        return input;
    }

    private String onlyNumbers(String input){
        if (!input.matches("\\d*")) {
            return (input.replaceAll("[^\\d]", ""));
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
    private String[] splitPostCode(String code){
        StringBuilder zip = new StringBuilder();
        StringBuilder city = new StringBuilder();
        int i;
        for (i = 0; i < code.length(); i++){
            if (i == 5){
                break;
            }
            if (Character.isDigit(code.charAt(i))){
                zip.append(code.charAt(i));
            } else {
                break;
            }
        }
        return new String[]{zip.toString(), city.append(code.substring(i)).toString()};
    }
    @Override
    public void update() {
        name.setText(customer.getFirstName() + " " + customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        email.setText(customer.getEmail());
        String[] postCode = splitPostCode(customer.getPostCode());
        address.setText(customer.getAddress() + ", " + postCode[0] + ", " + postCode[1]);

    }

    @Override
    public void open(){
        update();
    }
}
