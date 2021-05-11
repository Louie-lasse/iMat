package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Locale;

public class HandlaPage extends Page{
    public HandlaPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handla.fxml"));
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
        update();
    }

    @Override
    protected void initialize(){
        //fix components
    }

    @Override
    public void update(){
        populateCardView();
    }

    private void populateCardView(){

    }

    public void categoryClicked(Locale.Category category) {
        //TODO write
    }
}
