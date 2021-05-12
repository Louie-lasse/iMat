package resources;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final List<Page> pages = new ArrayList<>();

    private int currentPageIndex = 0;

    @FXML
    StackPane PageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /*
        pages.add(new HandlaPage());
        pages.add(new VarukorgPage());
        pages.add(new LeveransPage());
        pages.add(new KassaPage());
        PageView.getChildren().clear();
        PageView.getChildren().addAll(pages);
        pages.get(currentPageIndex).toFront();

         */
        Page.setParent(this);
        PageView.getChildren().clear();
        PageView.getChildren().add(new HandlaPage());

    }

    @FXML
    public void showPreviousWindow(){
        pages.get(currentPageIndex).toBack();
        currentPageIndex -= 1;
    }

    public void showNextWindow(){
        pages.get(currentPageIndex).toFront();
        currentPageIndex += 1;
    }

    public void updateCartPrice(){

    }

    public void returnHome(){

    }
}
