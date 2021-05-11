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

    @FXML
    StackPane PageView;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pages.add(new HandlaPage());
    }

    public void showPreviousWindow(){

    }

    public void showNextWindow(){

    }

    public void updateCartPrice(){

    }

    public void returnHome(){

    }
}
