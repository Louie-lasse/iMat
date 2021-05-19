package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Customer;

public class LeveransPage extends Page{

    private static final Customer customer = db.getCustomer();

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField email;

    @FXML
    private TextField address;

    @FXML
    private TextField zipCode;

    @FXML
    private TextField city;

    @FXML
    private TextField date;

    @FXML
    private TextField time;

    @FXML
    private TextArea message;

    @FXML
    private ImageView nameCheck;

    @FXML
    private ImageView nameX;

    @FXML
    private ImageView surnameCheck;

    @FXML
    private ImageView surnameX;

    @FXML
    private ImageView phoneNumberCheck;

    @FXML
    private ImageView phoneNumberX;

    @FXML
    private ImageView emailCheck;

    @FXML
    private ImageView emailX;

    @FXML
    private ImageView addressCheck;

    @FXML
    private ImageView addressX;

    @FXML
    private ImageView zipCheck;

    @FXML
    private ImageView zipX;

    @FXML
    private ImageView cityCheck;

    @FXML
    private ImageView cityX;

    @FXML
    protected void nameEnter(ActionEvent actionEvent){
        checkName(name.getText());
    }

    @FXML
    protected void surnameEnter(ActionEvent actionEvent){
        checkSurname(surname.getText());
    }

    @FXML
    protected void phoneNumberEnter(ActionEvent actionEvent){
        checkPhoneNumber(phoneNumber.getText());
    }

    @FXML
    protected void emailEnter(ActionEvent actionEvent){
        checkEmail(email.getText());
    }

    @FXML
    protected void addressEnter(ActionEvent actionEvent){
        checkAddress(address.getText());
    }

    @FXML
    protected void zipCodeEnter(ActionEvent actionEvent){
        checkZipCode(zipCode.getText());
    }

    @FXML
    protected void cityEnter(ActionEvent actionEvent){
        checkCity(city.getText());
    }


    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leverans.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    protected void initialize() {
        name.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkName(name.getText());
            }
        });
        surname.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkSurname(surname.getText());
            }
        });
        phoneNumber.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkPhoneNumber(phoneNumber.getText());
            }
        });
        email.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkEmail(email.getText());
            }
        });
        address.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkAddress(address.getText());
            }
        });
        zipCode.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkZipCode(zipCode.getText());
            }
        });
        city.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkCity(city.getText());
            }
        });
        checkFields();
    }

    private void checkName(String s){
        boolean validName = db.isValidName(s);
        nameCheck.setVisible(validName);
        nameX.setVisible(!validName);
        if (validName){
            customer.setFirstName(s);
        }
    }

    private void checkSurname(String s){
        boolean validName = db.isValidName(s);
        surnameCheck.setVisible(validName);
        surnameX.setVisible(!validName);
        if (validName){
            customer.setLastName(s);
        }
    }

    private void checkPhoneNumber(String s){
        boolean validNumber = db.isValidNumber(s);
        phoneNumberCheck.setVisible(validNumber);
        phoneNumberX.setVisible(!validNumber);
        if (validNumber){
            customer.setPhoneNumber(s);
        }
    }

    private void checkEmail(String s){
        boolean validEmail = db.isValidEmail(s);
        emailCheck.setVisible(validEmail);
        emailX.setVisible(!validEmail);
        if (validEmail){
            customer.setEmail(s);
        }
    }

    private void checkAddress(String s){
        boolean validAddress = db.isValidAddress(s);
        addressCheck.setVisible(validAddress);
        addressX.setVisible(!validAddress);
        if (validAddress){
            customer.setAddress(s);
        }
    }

    private void checkZipCode(String s){
        boolean validZip = db.isValidZip(s);
        zipCheck.setVisible(validZip);
        zipX.setVisible(!validZip);
        if (validZip){
            String city = splitPostCode(customer.getPostCode())[1];
            customer.setPostCode(zipCode.getText()+city);
        }
    }

    private void checkCity(String s){
        boolean validCity = db.isValidCity(s);
        cityCheck.setVisible(validCity);
        cityX.setVisible(!validCity);
        if (validCity){
            String zip = splitPostCode(customer.getPostCode())[0];
            customer.setPostCode(zip+city.getText());
        }
    }


    @Override
    public void update() {
        name.setText(customer.getFirstName());
        surname.setText(customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        String[] postCode = splitPostCode(customer.getPostCode());
        zipCode.setText(postCode[0]);
        city.setText(postCode[1]);
        checkFields();
    }

    private void checkFields() {
        checkName(customer.getFirstName());
        checkSurname(customer.getLastName());
        checkPhoneNumber(customer.getPhoneNumber());
        checkEmail(customer.getEmail());
        checkAddress(customer.getAddress());
        String[] postCode = splitPostCode(customer.getPostCode());
        checkZipCode(postCode[0]);
        checkCity(postCode[1]);
    }

    @Override
    public void open(){
        update();
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



}
