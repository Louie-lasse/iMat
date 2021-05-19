package resources;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import se.chalmers.cse.dat216.project.IMatDataHandler;

import java.io.IOException;

public abstract class Page extends AnchorPane {

    Page(){
        FXMLLoader fxmlLoader = getFxmlLoader();
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        initialize();
        update();
    }
    protected abstract FXMLLoader getFxmlLoader();
    protected static BackendAdapter db = BackendAdapter.getInstance();
    protected static MenuController parent;
    public static void setParent(MenuController menuController){ parent = menuController; }
    public abstract void update();
    protected abstract void initialize();
    public abstract void open();
    public boolean isDone(){
        return true;
    }
    public void displayErrors(){}
}
