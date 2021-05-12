package resources;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Locale;

public class CategoryController extends AnchorPane {

    private final Locale.Category category;

    private static HandlaPage parentPage;

    private String mainColor;

    private final int depth;

    private String hoverColor;

    private String pressColor;

    @FXML
    private AnchorPane categoryItem;

    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView categoryArrow;

    public CategoryController(Locale.Category category, int depth){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("categoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.category = category;
        this.depth = depth;
        setColors();
    }

    public static void setParentPage(HandlaPage page){
        parentPage = page;
    }

    private void setColors(){
        categoryLabel.setText(category.toString());
        switch(depth){
            case(0):
                setMainColors();
                break;
            case(1):
                setSubColors();
                moveIn(1);
                break;
            case(2):
                setSubSubColors();
                moveIn(2);
                break;
        }
        categoryItem.setStyle(mainColor);
    }

    private void setSelectedColors(){
        categoryLabel.getStyleClass().clear();
        categoryLabel.getStyleClass().add("selected");
    }

    private void setMainColors(){
        categoryItem.getStyleClass().clear();
        categoryLabel.getStyleClass().add("headCategory");
        //TODO fixa färger
    }

    private void setSubColors(){
        categoryItem.getStyleClass().clear();
        categoryLabel.getStyleClass().add("subCategory");
        //Todo set other colors
    }

    private void setSubSubColors(){
        mainColor = "-fx-background-color:  #FFB881";
        //Todo fixa färger
    }

    private void moveIn(int length){
        categoryItem.setLayoutX(10*length);
    }

    @FXML
    protected void onClick(){
        parentPage.categoryClicked(category);
    }

    public void toggleOn(){
        categoryArrow.setRotate(270);
        setSelectedColors();
    }
    public void toggleOff(){
        categoryArrow.setRotate(0);
        setColors();
    }
}
