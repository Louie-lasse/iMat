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
        populateCardView();
    }

    private void initialize(){
        //fix components
    }

    private void populateCardView(){

    }
}
