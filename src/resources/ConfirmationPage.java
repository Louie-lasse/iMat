package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import resources.TextFiles.Name;

import java.awt.event.MouseEvent;

public class ConfirmationPage extends Page{

    @FXML Button homeButton;
    @FXML Button receiptButton;
    @FXML Label deliveryName;

    @FXML Label arriveTime;
    @FXML Label arriveDate;
    @FXML Label arriveAddress;
    @FXML Label arrivePost;

    private KassaPage kassaPage;

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }


    @Override
    public void update() {
        arriveAddress.setText(kassaPage.address.getText());
        arriveTime.setText(kassaPage.time.getText());
        arriveDate.setText(kassaPage.date.getText());
        arrivePost.setText(BackendAdapter.getInstance().getCustomer().getPostCode() + ", " +
                BackendAdapter.getInstance().getCustomer().getPostAddress());
    }

    public Button getReceiptButton() {
        return receiptButton;
    }

    public Button getHomeButton() {
        return homeButton;
    }

    @Override
    protected void initialize() {
        deliveryName.setText("Leverans sker av " + Name.get());
        kassaPage = new KassaPage();
    }

    @Override
    public void open() {
        update();
    }
}
