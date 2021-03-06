package resources;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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

    @FXML private TextField searchBar;
    @FXML TextArea textArea;
    @FXML
    ScrollPane normalView;
    @FXML AnchorPane progressBar;
    @FXML AnchorPane popup;
    @FXML AnchorPane varukorgPopup;
    @FXML AnchorPane varukorgPopupIcon;
    @FXML AnchorPane helpPopup;
    @FXML AnchorPane helpPopupIcon;
    @FXML AnchorPane profilePopupIcon;
    @FXML AnchorPane homeButtonIcon;

    @FXML
    private AnchorPane backButton;

    @FXML
    private AnchorPane forwardButton;

    @FXML private Label pageNotComplete;

    @FXML Label cartValue;

    @FXML FlowPane cartFlowPane;

    @FXML AnchorPane searchPane;
    @FXML AnchorPane varukorgPopupOption;
    @FXML AnchorPane profilPopupOption;
    @FXML Button info;
    @FXML Button guide;
    @FXML Button service;

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
    @FXML Label headerLabel;
    @FXML Label cartPrice;
    //VARUKORG POPUP
    private final HashMap<Product, CartItemController> controllerHashMap = new HashMap<>();

    private HandlaPage handlaPage;

    @FXML
    private Label totalPrice;
    private ProfilePage profilePagePopup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Page.setParent(this);
        handlaPage = new HandlaPage();
        pages.add(handlaPage);
        pages.add(new VarukorgPage());
        pages.add(new LeveransPage());
        //TODO move init methods to respective page, removing need for page as attribute variable
        pages.add(new KassaPage());
        pages.add(new ConfirmationPage());
        PageView.getChildren().clear();
        PageView.getChildren().addAll(pages);
        normalView.toFront();
        pages.get(currentPageIndex).toFront();
        pages.get(currentPageIndex).open();
        varukorgPopupTuple = new PopupTuple(varukorgPopup, varukorgPopupIcon);

        profilePagePopup = new ProfilePage();
        profilePopupTuple = new PopupTuple(profilePagePopup, profilePopupIcon);
        popup.getChildren().add(profilePagePopup);
        helpPopupTuple = new PopupTuple(helpPopup, helpPopupIcon);

        openHomeIcon();
        updateWizardButtons();
    }
    @FXML
    void moreInfo(){
        headerLabel.setText("Om iMat:");
        textArea.setText("H??r p?? iMat vill vi g??ra det l??ttare f??r dig att handla. " +
                "Slipp smittrisken i butiker och obekv??mligheterna med att ??ka fram och tillbaka. " +
                "V??rt m??l ??r att g??ra sidan s??" +
                "l??tt att anv??nda som m??jligt s?? att du kan l??gga tid p?? annat.\n" +
                "\n" +
                "Vi fr??n iMat ??nskar dig ett h??rligt handlande!");
    }
    @FXML
    void guideInfo(){
        headerLabel.setText("En guide till att anv??nda sidan:");
        textArea.setText("1: L??gg varorna i varukorgen.\n2: Granska varukorgen s?? allt finns med.\n3: Ange leveransuppgifter.\n" +
                "4: Dubbelkolla s?? att alla uppgifter st??mmer och fullborda k??pet.\n5: Luta dig tillbaka och inv??nta leverans." );
    }
    @FXML
    void customerService(){
        headerLabel.setText("Kontaktinformation till kundtj??nst:");
        textArea.setText("Hej!\nVill du komma i kontakt med v??r kundtj??nst?\n Telefon: 070 123 23 23\nE-post: info@imat.se");
    }
    public String getSearchString(){
        return searchBar.getText();
    }

    public boolean searchBarIsEmpty(){
        return searchBar.getText().equals("");
    }

    public void resetSearchBar(){
        searchBar.setText("");
    }

    @FXML
    public void showPreviousWindow(){
        closePopup();
        currentPageIndex -= 1;
        PageView.toFront();
        pages.get(currentPageIndex).toFront();
        pages.get(currentPageIndex).open();
        updateWizardButtons();
    }

    @FXML
    public void showNextWindow(){
        closePopup();
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
    void closePopup() {
        hidePopup();
    }

    public void hidePopup(){
        varukorgPopupTuple.closeIcon();
        profilePopupTuple.closeIcon();
        helpPopupTuple.closeIcon();
        popup.toBack();
        openHomeIcon();
    }

    @FXML
    void openVarukorgPopup(MouseEvent event) {
        varukorgPopupTuple.open();
        profilePopupTuple.close();
        helpPopupTuple.close();
        closeHomeIcon();
        popup.toFront();
        updateCartPopup();
    }

    @FXML
    void openProfilePopup(){
        profilePagePopup.open();
        varukorgPopupTuple.close();
        profilePopupTuple.open();
        helpPopupTuple.close();
        closeHomeIcon();
        popup.toFront();
    }

    public void updatePrice(){
        cartPrice.setText((double) Math.round(db.getTotalPrice() * 100) / 100 + " kr");
    }


    @FXML
    void openHelpPopup(MouseEvent event) {
        varukorgPopupTuple.close();
        profilePopupTuple.close();
        helpPopupTuple.open();
        closeHomeIcon();
        popup.toFront();
    }
    @FXML public void changeInfo(){
        setPageToFront(2);
    }

    public Label getPageNotComplete() {
        return pageNotComplete;
    }

    private void updateWizardButtons(){
        pageNotComplete.setVisible(false);
        switch (currentPageIndex) {
            case 0:
                normalView.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
                normalView.setVmax(1);
                normalView.setVvalue(0);
                searchPane.setVisible(true);
                varukorgPopupOption.setVisible(true);
                profilPopupOption.setVisible(true);
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
                openHomeIcon();
                break;
            case 1:
                closeHomeIcon();
                normalView.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                normalView.setVvalue(0);
                normalView.setVmax(0.2);
                backButton.setVisible(true);
                searchPane.setVisible(false);
                profilPopupOption.setVisible(true);
                varukorgPopupOption.setVisible(false);
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
                normalView.setVvalue(0);
                normalView.setVmax(0.2);
                profilPopupOption.setVisible(false);
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
                normalView.setVmax(0.2);
                normalView.setVvalue(0);
                progressBar3.toFront();
                progress4.toFront();
                checkBox3.toFront();
                progress4Label.toFront();
                progress4Label.setStyle("-fx-text-fill: white");
                forwardButton.setVisible(false);
                break;
            case 4:
                checkBox4.toFront();
                backButton.setVisible(false);
        }
    }

    public void search(){
        handlaPage.search();
        closePopup();
    }

    public void returnHome(){
        setPageToFront(0);
        openHomeIcon();
        updatePrice();
        hidePopup();
    }

    private void openHomeIcon(){
        ObservableList<String> css = homeButtonIcon.getStyleClass();
        css.clear();
        css.add("icon-open");
        AnchorPane.setBottomAnchor(homeButtonIcon, 0.0);
    }

    private void closeHomeIcon(){
        ObservableList<String> css = homeButtonIcon.getStyleClass();
        css.clear();
        css.add("icon-open");
        css.add("icon-closed");
        AnchorPane.setBottomAnchor(homeButtonIcon, 13.0);
    }

    private void setPageToFront(int num){
        this.currentPageIndex = num;
        pages.get(this.currentPageIndex).toFront();
        normalView.setVvalue(0);
        updateWizardButtons();
    }


    public void updateCartPopup() {
        List<Product> products = db.getCartProducts();
        CartItemController cartItemController;
        List<CartItemController> controllers = new ArrayList<>();
        boolean color = true;
        for (Product p : products) {
            cartItemController = getCartItemController(p);
            if(color){
                cartItemController.setStyle("-fx-background-color: #ededed");
            }else{
                cartItemController.setStyle("-fx-background-color: white");
            }
            color = !color;
            controllers.add(cartItemController);
        }
        List<Node> flowPaneChildren = cartFlowPane.getChildren();
        flowPaneChildren.clear();
        flowPaneChildren.addAll(controllers);
        totalPrice.setText("Totalt: " + (double) Math.round(db.getTotalPrice()*100) / 100 + " kr");
    }

    CartItemController getCartItemController(Product p){
        CartItemController controller = controllerHashMap.get(p);
        if (controller==null){
            controller = new CartItemController(p);
            controllerHashMap.put(p, controller);
        } else {
            controller.update();
        }
        return controller;
    }

    public void update(){
        if (pages.get(currentPageIndex).isDone()){
            pageNotComplete.setVisible(false);
        } else {
            pageNotComplete.setVisible(true);
        }
    }

    void varukorgUpdated(){
        handlaPage.varukorgUpdated();
        updatePrice();
    }
}
