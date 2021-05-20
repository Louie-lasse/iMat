package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;

public class ConfirmationPage extends Page{

    @FXML Button homeButton;
    @FXML Button receiptButton;

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }


    @Override
    public void update() {

    }

    public Button getReceiptButton() {
        return receiptButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void open() {

    }
}
