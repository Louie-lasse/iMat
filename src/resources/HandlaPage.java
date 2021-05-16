package resources;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class HandlaPage extends Page{

    private Category activeCategory = Category.HOME;

    private final HashMap<Tree, TitledPane> treeTitledPaneHashMap = new HashMap<>();

    private final HashMap<Control, Tree> controlTreeHashMap = new HashMap<>();

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
        List<Tree> subItems = Tree.get(Category.HOME).getChildren();

        for (Tree child: subItems){
            panes.add(getItem(child));
        }

    }

    private TitledPane getItem(Tree tree){
        VBox subItems = new VBox();
        List<Tree> children = tree.getChildren();
        for (Tree child: children){
            if (!child.hasSubcategory()){
                AnchorPane pane = new AnchorPane();
                TextField text = new TextField(child.toString());
                text.setEditable(false);
                text.setOnMouseClicked(this::handleCategoryItemClicked);
                controlTreeHashMap.put(text, child);
                pane.getChildren().add(text);
                //todo add styling
                //TODO add clickable
                subItems.getChildren().add(pane);
            } else {
                TitledPane pane = getItem(child);
                pane.setOnMouseClicked(this::handleCategoryItemClicked);
                subItems.getChildren().add(pane);
            }
        }
        //TODO add clickable
        //TODO add items to maps
        TitledPane item = new TitledPane(tree.toString(), subItems);
        item.setExpanded(false);
        controlTreeHashMap.put(item, tree);
        treeTitledPaneHashMap.put(tree, item);
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
        Tree selected = Tree.get(activeCategory);

        if (selected.hasSubcategory()){
            if (activeCategory != Category.HOME) {
                List<Tree> siblings = selected.getParent().getChildren();
                for (Tree sibling: siblings){
                    if (sibling.hasSubcategory())
                        treeTitledPaneHashMap.get(sibling).setExpanded(false);
                }
                treeTitledPaneHashMap.get(selected).setExpanded(true);
            }
            List<Tree> children = selected.getChildren();
            for (Tree child: children){
                if (child.hasSubcategory())
                    treeTitledPaneHashMap.get(child).setExpanded(false);    //Removes expansion from all children
            }
        }
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

    public void handleCategoryItemClicked(Event event){
        Tree clicked = controlTreeHashMap.get(event.getSource());
        //"suspicious" cuz getSource returns Object, but only Control (parent class of TitledPane and TextField)
        //are connected to the method, so the map SHOULD never return null (or crash)
        activeCategory = clicked.getCategory();
        update();
    }



}
