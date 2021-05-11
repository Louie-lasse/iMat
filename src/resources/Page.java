package resources;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public abstract class Page extends AnchorPane {
    protected static IMatDataHandler db = IMatDataHandler.getInstance();
    protected static MenuController parent;
    public static void setParent(MenuController menuController){ parent = menuController; }
    public abstract void update();
    protected abstract void initialize();
}
