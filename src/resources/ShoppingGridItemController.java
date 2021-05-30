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
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.FileInputStream;
import java.io.IOException;

public class ShoppingGridItemController extends AnchorPane {

    @FXML private Label nameLabel;

    @FXML private Label priceLabel;

    @FXML private ImageView productImage;

    @FXML private TextField amount;

    private Product product;

    private Unit unit;
    @FXML ImageView ecoIcon;

    @FXML private ImageView minus;
    @FXML private ImageView plus;

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

    private static final BackendAdapter db = BackendAdapter.getInstance();

    private static HandlaPage parent;

    public ShoppingGridItemController(Product product){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingGridItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }


        //Anropa init

        this.product=product;

        initialize();
        update();
    }

    public static void setParent(HandlaPage page){
        parent = page;
    }

    private String onlyNumbers(String input){
        if (!input.matches("\\d*")) {
            return (input.replaceAll("[^\\d]", ""));
        }
        return input;
    }


    private void initialize(){
        // Kan behövas för att synliggöra rundade hörn// scene.setFill(Color.TRANSPARENT); //Makes the back-background of the cards transparent
        // Kan behövas för att synliggöra rundade hörn// stage.initStyle(StageStyle.TRANSPARENT); //Makes the back-background of the cards transparent
        ecoIcon.setVisible(product.isEcological());
        nameLabel.setText(product.getName());
        unit = Unit.get(product);
        priceLabel.setText(Double.toString(product.getPrice())+" "+unit);
        productImage.setImage(db.getFXImage(product));
        minusExited();
    }

    @FXML
    protected void add(){
        db.addProduct(this.product);
        internalUpdate();
        parent.itemAmountChanged();
        minusExited();
    }

    @FXML
    protected void subtract(){
        db.subtractProduct(this.product);
        internalUpdate();
        parent.itemAmountChanged();
        minusHover();
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

    private void internalUpdate(){
        amount.setText(db.getFormattedAmount(product));
        format();
    }

    public void update(){
        internalUpdate();
        minusExited();
    }
    private void format(){
        ObservableList<String> textStyle = amount.getStyleClass();
        textStyle.clear();
        if (db.getAmount(product) > 0){
            textStyle.add("orange-text-field");
        } else {
            textStyle.add("text-field-empty");
        }
    }
}
