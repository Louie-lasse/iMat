package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HandlaPage extends Page{

    private Category activeCategory = Category.HOME;

    private final EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            System.out.println("YESS!!!");
            throw new IllegalArgumentException();
        }
    };

    private final HashMap<Category, TitledPane> hashMap = new HashMap<>();

    @FXML
    private Accordion categoryMenu;

    @FXML
    private Label breadCrumbs;

    public HandlaPage() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handla.fxml"));
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
    protected void initialize(){
        List<TitledPane> panes = categoryMenu.getPanes();
        panes.clear();
        List<Category> categories = Tree.get(Category.HOME).getSubcategory();
        List<Tree> subItems = Tree.get(Category.HOME).getChildren();

        for (Tree child: subItems){
            panes.add(getItem(child));
        }

        /*
        for (Category category: categories){
            Node subItem = getItem(category);
            subItem.setOnMouseClicked(ev -> openCategory(category));
        }

         */
    }

    private TitledPane getItem(Tree tree){
        VBox subItems = new VBox();
        List<Tree> children = tree.getChildren();
        for (Tree child: children){
            if (!child.hasSubcategory()){
                AnchorPane pane = new AnchorPane();
                pane.getChildren().add(new TextField(child.toString()));
                //todo add styling
                //TODO add clickable
                subItems.getChildren().add(pane);
            } else {
                TitledPane pane = getItem(child);
                pane.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
                //pane.setOnMouseClicked(this::handleTitlePaneClicked);
                subItems.getChildren().add(getItem(child));
            }
        }
        //TODO add clickable
        TitledPane item = new TitledPane(tree.toString(), subItems);
        item.setExpanded(false);
        hashMap.put(tree.getCategory(), item);
        return item;
    }

    @Override
    public void update(){
        populateCardView();
        updateCategoryMenu();
        updateBreadcrumbs();
    }

    private void populateCardView(){

    }

    private void updateCategoryMenu(){

    }

    private void updateBreadcrumbs(){
        StringBuilder sb = new StringBuilder();
        List<Category> path = Tree.get(activeCategory).getSearchPath();
        for (Category category: path){
            sb.append('/');
            sb.append(category.toString());
        }
        sb.deleteCharAt(0);
        breadCrumbs.setText(sb.toString());
    }

    private void handleTitlePaneClicked(Event event){
        int x = 3;
        int y = 1;
    }



    public void categoryClicked(Category category) {
        activeCategory = category;
        update();
    }
}
