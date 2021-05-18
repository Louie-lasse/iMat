package resources;

import javafx.fxml.FXMLLoader;

public class ConfirmationPage extends Page{


    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Confirmation.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        return fxmlLoader;
    }


    @Override
    public void update() {

    }

    @Override
    protected void initialize() {

    }

    @Override
    public void open() {

    }
}
