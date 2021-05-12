package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class KassaPage extends Page {

    public KassaPage(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kassa.fxml"));
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

    @Override
    public void update() {

    }

    @Override
    protected void initialize() {

    }
}
