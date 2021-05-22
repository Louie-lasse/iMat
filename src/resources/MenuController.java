package resources;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final List<Page> pages = new ArrayList<>();

    private final BackendAdapter db = BackendAdapter.getInstance();

    private int currentPageIndex = 0;

    private PopupTuple varukorgPopupTuple;

    private PopupTuple profilePopupTuple;

    private PopupTuple helpPopupTuple;

    @FXML StackPane PageView;

    @FXML
    ScrollPane normalView;
    @FXML AnchorPane progressBar;
    @FXML AnchorPane popup;
    @FXML AnchorPane varukorgPopup;
    @FXML AnchorPane varukorgPopupIcon;
    @FXML AnchorPane helpPopup;
    @FXML AnchorPane helpPopupIcon;
    @FXML AnchorPane profilePopupIcon;

    @FXML
    private AnchorPane backButton;

    @FXML
    private AnchorPane forwardButton;

    @FXML private Label pageNotComplete;

    @FXML Label cartValue;

    @FXML FlowPane cartFlowPane;

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

    private int prevPageIndex = 0;
    //VARUKORG POPUP
    private final HashMap<Product, CartItemController> controllerHashMap = new HashMap<>();


    @FXML
    private Label totalPrice;
    private ConfirmationPage confirmationPage;
    private KassaPage kassaPage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages.add(new HandlaPage());
        pages.add(new VarukorgPage());
        pages.add(new LeveransPage());
        //TODO move init methods to respective page, removing need for page as attribute variable
        kassaPage = new KassaPage();
        pages.add(kassaPage);
        confirmationPage = new ConfirmationPage();
        pages.add(confirmationPage);
        PageView.getChildren().clear();
        PageView.getChildren().addAll(pages);
        PageView.toFront();
        pages.get(currentPageIndex).toFront();
        pages.get(currentPageIndex).open();

        varukorgPopupTuple = new PopupTuple(varukorgPopup, varukorgPopupIcon);

        ProfilePage profilePagePopup = new ProfilePage();
        profilePopupTuple = new PopupTuple(profilePagePopup, profilePopupIcon);
        popup.getChildren().add(profilePagePopup);

        helpPopupTuple = new PopupTuple(helpPopup, helpPopupIcon);

        Page.setParent(this);
        updateWizardButtons();

        List<Product> products = db.getCartProducts();
        for (Product p: products){
            controllerHashMap.put(p, new CartItemController(p));
        }

        confirmationPage.homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                returnHome();
            }
        });

        kassaPage.payButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (kassaPage.checkInfo()) {
                    kassaPage.saveCardInfo();
                    showNextWindow();
                    db.clearCart();
                }else{
                    pageNotComplete.setVisible(true);
                }
            }
        });

        confirmationPage.receiptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                openProfilePopup();
            }
        });
    }

    @FXML
    public void showPreviousWindow(){
        currentPageIndex -= 1;
        PageView.toFront();
        pages.get(currentPageIndex).toFront();
        pages.get(currentPageIndex).open();
        updateWizardButtons();
    }

    @FXML
    public void showNextWindow(){
        Page currentPage = pages.get(currentPageIndex);
        if (currentPage.isDone()) {
            currentPageIndex += 1;
            PageView.toFront();
            pages.get(currentPageIndex).toFront();
            pages.get(currentPageIndex).open();
            updateWizardButtons();
        } else {
            currentPage.displayErrors();
            pageNotComplete.setVisible(true);
        }
    }

    @FXML
    void closePopup(MouseEvent event) {
        hidePopup();
    }

    public void hidePopup(){
        varukorgPopupTuple.closeIcon();
        profilePopupTuple.closeIcon();
        helpPopupTuple.closeIcon();
        popup.toBack();
    }

    @FXML
    void openVarukorgPopup(MouseEvent event) {
        varukorgPopupTuple.open();
        profilePopupTuple.close();
        helpPopupTuple.close();
        popup.toFront();
    }

    @FXML
    void openProfilePopup(){
        varukorgPopupTuple.close();
        profilePopupTuple.open();
        helpPopupTuple.close();
        popup.toFront();
    }


    @FXML
    void openHelpPopup(MouseEvent event) {
        varukorgPopupTuple.close();
        profilePopupTuple.close();
        helpPopupTuple.open();
        popup.toFront();
    }

    private void updateWizardButtons(){
        pageNotComplete.setVisible(false);
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
                //forwardButton.setVisible(false);
                break;
            case 4:
                checkBox4.toFront();
                backButton.setVisible(false);
        }
    }

    public void search(){

    }
    public void returnHome(){
        setPageToFront(0);
        hidePopup();
        updateWizardButtons();
    }

    private void setPageToFront(int num){
        this.currentPageIndex = num;
        pages.get(this.currentPageIndex).toFront();
        pages.get(this.currentPageIndex).open();
    }


    public void updateCartPopup() {
        List<Product> products = db.getCartProducts();
        CartItemController cartItemController;
        List<CartItemController> controllers = new ArrayList<>();
        for (Product p : products) {
            cartItemController = controllerHashMap.get(p);
            if (cartItemController == null) {
                cartItemController = new CartItemController(p);
                controllerHashMap.put(p, cartItemController);
            }
            controllers.add(cartItemController);
        }
        List<Node> flowPaneChildren = cartFlowPane.getChildren();
        flowPaneChildren.clear();
        flowPaneChildren.addAll(controllers);
        totalPrice.setText("Totalt: " + db.getTotalPrice());
    }
}
