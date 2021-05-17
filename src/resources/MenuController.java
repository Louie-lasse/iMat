package resources;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final List<Page> pages = new ArrayList<>();

    private final BackendAdapter db = BackendAdapter.getInstance();

    private int currentPageIndex = 0;

    @FXML StackPane PageView;

    @FXML FlowPane normalView;
    @FXML AnchorPane progressBar;
    @FXML AnchorPane popup;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

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
        if (currentPageIndex == 0) {
            backButton.setVisible(false);
            forwardButton.setVisible(true);
        } else {
            backButton.setVisible(true);
            if (currentPageIndex==3){
                forwardButton.setVisible(false);
            } else{
                forwardButton.setVisible(true);
            }
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
        progressBar.toFront();
        normalView.toFront();
    }
    public void showCart(){
        popup.toFront();
    }
    public void showProfile(){
        int temp = currentPageIndex;
        setPageToFront(4);

    }
    public void showHelp(){

    }
    private void setPageToFront(int num){
        this.currentPageIndex = num;
        pages.get(this.currentPageIndex).toFront();
    }
}
