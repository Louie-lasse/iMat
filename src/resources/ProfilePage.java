package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    @Override
    protected void initialize() {

    }

    public Button getBackButton() {
        return backButton;
    }

    @Override
    protected FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
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
        address.setText(customer.getAddress());
        zip.setText(postCode[0]);
        city.setText(postCode[1]);

    }
    @Override
    public void open(){
        update();
    }


}
