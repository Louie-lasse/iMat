package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class HandlaPage extends Page{
    public HandlaPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handla.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
        update();
    }

    private void initialize(){
        //fix components
    }

    public void update(){
        populateCardView();
    }

    private void populateCardView(){

    }
}
