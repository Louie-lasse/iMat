package resources;

import javafx.fxml.FXMLLoader;

public class ProfilePage extends Page{

    @Override
    protected void initialize() {

    }
    @Override
    protected FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    public void update() {

    }


}
