package resources;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }


    @Override
    public void update() {
        arriveAddress.setText(BackendAdapter.getInstance().getCustomer().getAddress());
        arrivePost.setText(BackendAdapter.getInstance().getCustomer().getPostCode() + ", " +
                BackendAdapter.getInstance().getCustomer().getPostAddress());
        arriveTime.setText(BackendAdapter.getInstance().getTime());
        arriveDate.setText(BackendAdapter.getInstance().getDate());

    }

    @Override
    protected void initialize() {
        deliveryName.setText("Leverans sker av " + Name.get());
        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                parent.returnHome();
            }
        });

        receiptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                parent.openProfilePopup();
            }
        });
    }

    @Override
    public void open() {
        update();
    }
}
