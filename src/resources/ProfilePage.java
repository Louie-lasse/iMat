package resources;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import se.chalmers.cse.dat216.project.Customer;


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

    }
    @Override
    public void open(){
        update();
    }

    @FXML
    protected void close(ActionEvent event){
        parent.hidePopup();
    }

}
