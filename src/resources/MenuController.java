package resources;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final List<Page> pages = new ArrayList<>();

    private final BackendAdapter db = BackendAdapter.getInstance();

    private int currentPageIndex = 0;

    @FXML StackPane PageView;

    @FXML
    ScrollPane normalView;
    @FXML AnchorPane progressBar;
    @FXML AnchorPane popup;
    @FXML AnchorPane varukorgPopup;
    @FXML AnchorPane helpPopup;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    //Progressbar indication
    @FXML Rectangle progressBar1;
    @FXML Rectangle progressBar2;
    @FXML Rectangle progressBar3;
    @FXML Label progress2Label;
    @FXML Label progress3Label;
    @FXML Label progress4Label;
    @FXML Circle progress2;
    @FXML Circle progress3;
    @FXML Circle progress4;
    @FXML Circle unfinishedStep2;
    @FXML Circle unfinishedStep3;
    @FXML Circle unfinishedStep4;
    @FXML ImageView checkBox1;
    @FXML ImageView checkBox2;
    @FXML ImageView checkBox3;
    @FXML ImageView checkBox4;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages.add(new HandlaPage());
        pages.add(new VarukorgPage());
        pages.add(new LeveransPage());
        pages.add(new KassaPage());
        pages.add(new ProfilePage());

        PageView.getChildren().clear();
        PageView.getChildren().addAll(pages);
        pages.get(currentPageIndex).toFront();
        Page.setParent(this);
        updateWizardButtons();
    }

    @FXML
    public void showPreviousWindow(){
        currentPageIndex -= 1;
        pages.get(currentPageIndex).toFront();
        updateWizardButtons();
    }

    public void showNextWindow(){
        currentPageIndex += 1;
        pages.get(currentPageIndex).toFront();
        updateWizardButtons();
    }

    private void updateWizardButtons(){
        switch (currentPageIndex) {
            case 0:
                backButton.setVisible(false);
                forwardButton.setVisible(true);
                unfinishedStep2.toFront();
                unfinishedStep3.toFront();
                unfinishedStep4.toFront();
                progress2Label.toFront();
                progress3Label.toFront();
                progress4Label.toFront();
                progress2Label.setStyle("-fx-text-fill: #999999");
                progress3Label.setStyle("-fx-text-fill: #999999");
                progress4Label.setStyle("-fx-text-fill: #999999");
                progressBar1.toBack();
                progressBar2.toBack();
                progressBar3.toBack();
                unfinishedStep2.toFront();
                progressBar1.toBack();
                progress2Label.toFront();
                checkBox1.toBack();
                break;
            case 1:
                backButton.setVisible(true);
                progressBar1.toFront();
                progress2.toFront();
                progress2Label.toFront();
                checkBox1.toFront();
                unfinishedStep3.toFront();
                progressBar2.toBack();
                progress3Label.toFront();
                progress3Label.setStyle("-fx-text-fill: #999999");
                progress2Label.setStyle("-fx-text-fill: white");
                break;
            case 2:
                progressBar2.toFront();
                progress3.toFront();
                checkBox2.toFront();
                progress3Label.toFront();
                progressBar3.toBack();
                progress3Label.setStyle("-fx-text-fill: white");
                unfinishedStep4.toFront();
                progress4Label.toFront();
                progress4Label.setStyle("-fx-text-fill: #999999");
                forwardButton.setVisible(true);
                break;
            case 3:
                progressBar3.toFront();
                progress4.toFront();
                checkBox3.toFront();
                progress4Label.toFront();
                progress4Label.setStyle("-fx-text-fill: white");
                forwardButton.setVisible(false);
                break;
        }
    }

    public void updateCartPrice(){

    }
    public void search(){

    }
    public void returnHome(){
        setPageToFront(0);
    }
    public void exitDetailed(){
        normalView.toFront();
        progressBar.toFront();

    }
    public void showCart(){
        popup.toFront();
        varukorgPopup.toFront();

    }
    public void showProfile(){
        setPageToFront(4);
    }
    /*
    public void getBack(){
        setPageToFront(this.prevPage);
    }

     */
    public void showHelp(){
        popup.toFront();
        helpPopup.toFront();
    }
    private void setPageToFront(int num){
        this.currentPageIndex = num;
        pages.get(this.currentPageIndex).toFront();
    }
}
