package resources;

import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

public class PopupTuple {
    private final AnchorPane pane;
    private final AnchorPane icon;

    PopupTuple(AnchorPane pane, AnchorPane icon){
        this.pane = pane;
        this.icon = icon;
    }

    public void open(){
        pane.setVisible(true);
        ObservableList<String> css = icon.getStyleClass();
        css.clear();
        css.add("icon-open");
        AnchorPane.setBottomAnchor(icon, 0.0);
    }

    public void close(){
        pane.setVisible(false);
        closeIcon();
    }

    public void closeIcon(){
        ObservableList<String> css = icon.getStyleClass();
        css.clear();
        css.add("icon-closed");
        AnchorPane.setBottomAnchor(icon, 13.0);
    }
}
