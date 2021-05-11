package resources;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.IMatDataHandler;

public abstract class Page extends AnchorPane {
    protected static IMatDataHandler db = IMatDataHandler.getInstance();
    protected static MenuController parent;
    public void setParent(MenuController menuController){ parent = menuController; }
    @FXML
    protected void previousButtonPressed(Event e){
        parent.showPreviousWindow();
    }
    @FXML
    protected void nextButtonPressed(Event e){
        parent.showNextWindow();
    }

}
