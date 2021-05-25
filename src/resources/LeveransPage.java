package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import se.chalmers.cse.dat216.project.Customer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.logging.SimpleFormatter;

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

    @FXML DatePicker datePicker;
    @FXML ImageView dateCheck;
    @FXML ImageView dateX;
    @FXML ImageView timeCheck;
    @FXML ImageView timeX;
    @FXML TextField hour;
    @FXML TextField min;

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
        timeCheck.setVisible(false);
        timeX.setVisible(false);
        dateCheck.setVisible(false);
        dateX.setVisible(false);
        datePicker.setStyle("-fx-font: 17px \"Arial\"");

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
        zipCode.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() < oldValue.length()){
                zipCode.setText(newValue);
            }else {
                if (zipCode.getText().length() == 3) {
                    zipCode.appendText(" ");
                }
                if(zipCode.getText().length() >= 6){
                    String temp = zipCode.getText().substring(0, 6);
                    zipCode.setText(temp);
                }
            }
        });
        city.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                checkCity(city.getText());
            }
        });
        datePicker.valueProperty().addListener((ov, oldValue, newValue) -> {
            checkDate();
        });
        hour.textProperty().addListener((observable, oldValue, newValue) -> {
            hour.setText(maxLength(hour.getText(), 2));
            hour.setText(onlyNumbers(hour.getText()));

            if(hour.getText().length() == 2){
                changeField(hour.getText(), 2, min);
                checkTime();
            }
        });
        min.textProperty().addListener((observable, oldValue, newValue) -> {
            min.setText(maxLength(min.getText(), 2));
            min.setText(onlyNumbers(min.getText()));

            if(min.getText().length() == 2){
                checkTime();
            }
        });
        checkFields();
    }
    public void checkDate() {
        ObservableList<String> styles = city.getStyleClass();
        styles.clear();

        if(datePicker.getValue() != null){
            if(db.isValidDate(datePicker.getValue())){
                dateCheck.setVisible(true);
                dateX.setVisible(false);
                styles.add("field-valid");

            }else {
            dateX.setVisible(true);
            dateCheck.setVisible(false);
            styles.add("field-invalid");

            }
        }
    }
    public void checkTime() {
        ObservableList<String> styles = name.getStyleClass();
        styles.clear();
        if(!hour.getText().isEmpty() && !min.getText().isEmpty()) {
            if (Integer.parseInt(hour.getText()) >= 0 && Integer.parseInt(hour.getText()) < 24 &&
                    Integer.parseInt(min.getText()) >= 0 && Integer.parseInt(min.getText()) < 60) {
                if(db.isValidTime(hour.getText(), min.getText())){
                    timeCheck.setVisible(true);
                    timeX.setVisible(false);
                    styles.add("field-valid");
                } else {
                    timeCheck.setVisible(false);
                    timeX.setVisible(true);
                    styles.add("field-invalid");

                }
            }
        }
    }
    private String onlyNumbers(String input){
        if (!input.matches("\\d*")) {
            return (input.replaceAll("[^\\d]", ""));
        }
        return input;
    }
    private void changeField(String input, int maxLength, TextField next){
        if(input.length() == maxLength){
            next.requestFocus();
        }
    }
    private String maxLength(String input, int maxLength){
        if(input.length() > maxLength){
            String temp = input.substring(0, maxLength);
            return temp;
        }
        return input;
    }
    private void checkName(String s){
        ObservableList<String> styles = name.getStyleClass();
        styles.clear();
        boolean validName = db.isValidName(s);
        nameCheck.setVisible(validName);
        nameX.setVisible(!validName);
        if (validName){
            customer.setFirstName(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkSurname(String s){
        ObservableList<String> styles = surname.getStyleClass();
        styles.clear();
        boolean validName = db.isValidName(s);
        surnameCheck.setVisible(validName);
        surnameX.setVisible(!validName);
        if (validName){
            customer.setLastName(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkPhoneNumber(String s){
        ObservableList<String> styles = phoneNumber.getStyleClass();
        styles.clear();
        boolean validNumber = db.isValidNumber(s);
        phoneNumberCheck.setVisible(validNumber);
        phoneNumberX.setVisible(!validNumber);
        if (validNumber){
            customer.setPhoneNumber(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkEmail(String s){
        ObservableList<String> styles = email.getStyleClass();
        styles.clear();
        boolean validEmail = db.isValidEmail(s);
        emailCheck.setVisible(validEmail);
        emailX.setVisible(!validEmail);
        if (validEmail){
            customer.setEmail(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkAddress(String s){
        ObservableList<String> styles = address.getStyleClass();
        styles.clear();
        boolean validAddress = db.isValidAddress(s);
        addressCheck.setVisible(validAddress);
        addressX.setVisible(!validAddress);
        if (validAddress){
            customer.setAddress(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkZipCode(String s){
        ObservableList<String> styles = zipCode.getStyleClass();
        styles.clear();
        boolean validZip = db.isValidZip(s);
        zipCheck.setVisible(validZip);
        zipX.setVisible(!validZip);
        if (validZip){
            customer.setPostCode(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }

    private void checkCity(String s){
        ObservableList<String> styles = city.getStyleClass();
        styles.clear();
        boolean validCity = db.isValidCity(s);
        cityCheck.setVisible(validCity);
        cityX.setVisible(!validCity);
        if (validCity){
            customer.setPostAddress(s);
            styles.add("field-valid");
        } else {
            styles.add("field-invalid");
        }
    }


    @Override
    public void update() {
        name.setText(customer.getFirstName());
        surname.setText(customer.getLastName());
        phoneNumber.setText(customer.getPhoneNumber());
        email.setText(customer.getEmail());
        address.setText(customer.getAddress());
        zipCode.setText(customer.getPostCode());
        city.setText(customer.getPostAddress());
        datePicker.setValue(LocalDate.now());
        hour.setText(LocalTime.now().toString().substring(0,2));
        min.setText(LocalTime.now().toString().substring(3,5));

        checkFields();
    }

    private void checkFields() {
        checkName(customer.getFirstName());
        checkSurname(customer.getLastName());
        checkPhoneNumber(customer.getPhoneNumber());
        checkEmail(customer.getEmail());
        checkAddress(customer.getAddress());

        checkZipCode(customer.getPostCode());
        checkCity(customer.getPostAddress());
        checkTime();
        checkDate();
    }

    @Override
    public void open(){
        update();
    }
    @Override
    public boolean isDone(){
        boolean everythingIsValid;
        everythingIsValid = db.isValidName(name.getText());
        everythingIsValid &= db.isValidName(surname.getText());
        everythingIsValid &= db.isValidNumber(phoneNumber.getText());
        everythingIsValid &= db.isValidEmail(email.getText());
        everythingIsValid &= db.isValidAddress(address.getText());
        everythingIsValid &= db.isValidCity(city.getText());
        everythingIsValid &= db.isValidZip(zipCode.getText());
        everythingIsValid &= db.isValidDate(datePicker.getValue());
        everythingIsValid &= db.isValidTime(hour.getText(), min.getText());

        if(datePicker.getValue() != null && hour.getText().length() > 0 && min.getText().length() > 0){
            db.setDate(datePicker.getValue().toString().substring(0,10));
            db.setTime(hour.getText() + ":" + min.getText());
        }else{
            return false;
        }
        return everythingIsValid;
    }

/*
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


 */

}
