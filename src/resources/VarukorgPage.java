package resources;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class VarukorgPage extends Page{

    public VarukorgPage(){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("varukorg.fxml"));
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

    }

    @Override
    public void update() {

    }
}
