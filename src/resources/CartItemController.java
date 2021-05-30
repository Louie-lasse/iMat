package resources;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.FileInputStream;
import java.io.IOException;

public class CartItemController extends AnchorPane {

    @FXML private ImageView ItemImage;
    @FXML private Label ItemNameLabel;
    @FXML private Label TotalPriceLabel;
    @FXML private Label PricePerPieceLabel;
    @FXML private TextField amount;
    @FXML private ImageView minus;
    @FXML private ImageView plus;
    private final Product product;
    private static final BackendAdapter db = BackendAdapter.getInstance();
    private static Page parent;
    private static final Image grayMinusHover = getGrayMinusHoverImage();
    private static final Image orangeMinusHover = getOrangeMinusHoverImage();
    private static final Image grayMinus = getGrayMinusImage();
    private static final Image orangeMinus = getOrangeMinusImage();
    private static final Image orangePlus = getPlusImage();
    private static final Image orangePlusHover = getPlusHoverImage();

    private static Image getGrayMinusHoverImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/grayHoverMinus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private static Image getGrayMinusImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/grayMinus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
    private static Image getOrangeMinusHoverImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/orangeHoverMinus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private static Image getOrangeMinusImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/orangeMinus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private static Image getPlusImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/orangePlus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private static Image getPlusHoverImage(){
        try {
            return new Image(new FileInputStream("src/resources/images/Buttons/hoverPlus.png"));
        } catch (Exception e){
            throw new RuntimeException();
        }
    }
    public CartItemController(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CartListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.product = product;
        init();
    }

    public void init(){
        ItemImage.setImage(db.getFXImage(product));
        ItemNameLabel.setText(product.getName());
        TotalPriceLabel.setText(Double.toString(product.getPrice()));
        PricePerPieceLabel.setText(Unit.get(product).toString());
        amount.setText(db.getFormattedAmount(product));
        format();
        minusExited();
    }

    @FXML
    void add(MouseEvent event) {
        db.addProduct(product);
        internalUpdate();
        minusExited();
    }

    @FXML
    void subtract(MouseEvent event) {
        db.subtractProduct(product);
        internalUpdate();
        minusHover();

    }

    @FXML
    void remove() {
        db.removeProduct(product);
        update();
    }

    public static void setParent(Page page){
        parent = page;
    }

    @FXML
    void minusHover(){
        Image activeImage;
        if (db.getAmount(product) > 0){
            activeImage = orangeMinusHover;
        } else {
            activeImage = grayMinusHover;
        }
        minus.setImage(activeImage);
    }

    @FXML
    void minusExited(){
        Image activeImage;
        if (db.getAmount(product) > 0){
            activeImage = orangeMinus;
        } else {
            activeImage = grayMinus;
        }
        minus.setImage(activeImage);
    }

    @FXML
    void plusHover(){
        plus.setImage(orangePlusHover);
    }

    @FXML
    void plusExited(){
        plus.setImage(orangePlus);
    }

    private void format(){
        ObservableList<String> textStyle = amount.getStyleClass();
        ObservableList<String> buttonStyle = minus.getStyleClass();
        textStyle.clear();
        buttonStyle.clear();
        if (db.getAmount(product) > 0){
            textStyle.add("orange-text-field");
            buttonStyle.add("orange-button");
        } else {
            textStyle.add("text-field-empty");
            buttonStyle.add("gray-button");
        }
        buttonStyle.add("round-button");
    }

    private void internalUpdate(){
        amount.setText(db.getFormattedAmount(product));
        format();
        parent.update();
    }

    public void update(){
        internalUpdate();
        minusExited();
    }


}
