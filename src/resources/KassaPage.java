package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class KassaPage extends Page {

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kassa.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    protected void initialize() {

    }

    @Override
    public void update() {

    }

    @Override
    public void open(){
        update();
    }
}
