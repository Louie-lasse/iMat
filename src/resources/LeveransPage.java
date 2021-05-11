package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LeveransPage extends Page{
    LeveransPage(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("leverans.fxml"));
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
