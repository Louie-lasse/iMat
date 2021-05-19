package resources;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandlaPage extends Page{

    private static Category activeCategory = Category.HOME;

    private final static HashMap<Tree, TitledPane> treeTitledPaneHashMap = new HashMap<>();

    private final static HashMap<Control, Tree> controlTreeHashMap = new HashMap<>();

    private final static HashMap<Product, ShoppingGridItemController> cardMap = new HashMap<>();

    @FXML
    private Accordion categoryMenu;

    @FXML
    private Label breadCrumbs;

    @FXML
    private TilePane productPane;

    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handla.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    protected void initialize(){
        List<TitledPane> panes = categoryMenu.getPanes();
        panes.clear();
        List<Tree> subItems = Tree.get(Category.HOME).getChildren();

        for (Tree child: subItems){
            panes.add(getItem(child));
        }

        List<Product> products = db.getAllProducts();
        for (Product product: products){
            cardMap.put(product, new ShoppingGridItemController(product));
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
                subItems.getChildren().add(pane);
            } else {
                TitledPane pane = getItem(child);
                subItems.getChildren().add(pane);
            }
        }
        //TODO add items to maps
        TitledPane item = new TitledPane(tree.toString(), subItems);
        item.setExpanded(false);
        item.setOnMouseClicked(this::handleCategoryItemClicked);
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

    @Override
    public void open(){
        activeCategory = Category.HOME;
        update();
    }

    private void populateCardView(){
        List<ShoppingGridItemController> activeCards = getSelectedProducts();
        List<Node> cards = productPane.getChildren();
        cards.clear();
        cards.addAll(activeCards);
    }

    private List<ShoppingGridItemController> getSelectedProducts(){
        List<Product> selectedProducts = db.getProducts(activeCategory);
        List<ShoppingGridItemController> selectedCards = new ArrayList<>();
        for (Product product: selectedProducts){
            selectedCards.add(cardMap.get(product));
        }
        return selectedCards;
    }

    private void updateCategoryMenu(){
        Tree selected = Tree.get(activeCategory);

        if (selected.hasSubcategory()){
            if (activeCategory != Category.HOME) {
                List<Tree> siblings = selected.getParent().getChildren();
                //TODO stäng sysskon även om this är text
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
