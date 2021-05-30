package resources;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
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

    private static SortingPriority sortingPriority = SortingPriority.NONE;

    private static SortingOrder sortingOrder = SortingOrder.REGULAR;

    @FXML
    private Accordion categoryMenu;

    @FXML
    private Label breadCrumbs;

    @FXML ScrollPane cardsScroll;

    @FXML
    private TilePane productPane;

    @FXML
    private ComboBox<SortingPriority> sortingPriorityComboBox;

    @FXML
    private Label inOrder;

    @FXML
    private ComboBox<SortingOrder> orderComboBox;


    @Override
    protected FXMLLoader getFxmlLoader(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handla.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        return fxmlLoader;
    }

    @Override
    protected void initialize(){
        ShoppingGridItemController.setParent(this);
        ObservableList<SortingPriority> items = sortingPriorityComboBox.getItems();
        items.add(SortingPriority.NONE);
        items.add(SortingPriority.PRICE);
        items.add(SortingPriority.ALPHABETICAL);
        items.add(SortingPriority.ECOLOGICAL);
        sortingPriorityComboBox.getSelectionModel().select(SortingPriority.NONE);
        sortingPriorityComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SortingPriority>() {
            @Override
            public void changed(ObservableValue<? extends SortingPriority> observableValue, SortingPriority oldPriority, SortingPriority t1) {
                sortingPriority = sortingPriorityComboBox.getSelectionModel().getSelectedItem();
                updateSortingOptions();
                update();
            }
        });
        inOrder.setVisible(false);
        orderComboBox.setVisible(false);
        orderComboBox.getItems().add(SortingOrder.REGULAR);
        orderComboBox.getItems().add(SortingOrder.REVERSE);
        orderComboBox.getSelectionModel().select(SortingOrder.REGULAR);
        orderComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SortingOrder>() {
            @Override
            public void changed(ObservableValue<? extends SortingOrder> observableValue, SortingOrder newOrder, SortingOrder t1) {
                sortingOrder = orderComboBox.getSelectionModel().getSelectedItem();
                update();
            }
        });
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
        subItems.getStyleClass().add("box-rules");
        List<Tree> children = tree.getChildren();
        for (Tree child: children){
            if (!child.hasSubcategory()){
                AnchorPane pane = new AnchorPane();
                TextField text = new TextField(child.toString());
                text.setEditable(false);
                text.setOnMouseClicked(this::handleCategoryItemClicked);
                text.setCursor(Cursor.HAND);
                text.getStyleClass().add("textrules");
                text.getStyleClass().add("font");
                controlTreeHashMap.put(text, child);
                pane.getChildren().add(text);
                subItems.getChildren().add(pane);
            } else {
                TitledPane pane = getItem(child);
                subItems.getChildren().add(pane);
            }
        }
        TitledPane item = new TitledPane(tree.toString(), subItems);
        item.setExpanded(false);
        item.setOnMouseClicked(this::handleCategoryItemClicked);
        item.getStyleClass().add("font");
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
        parent.resetSearchBar();
        update();
    }

    public void search(){
        activeCategory = Category.HOME;
        sortingPriorityComboBox.getSelectionModel().select(SortingPriority.NONE);
        update();
    }

    private void updateSortingOptions(){
        boolean noPriority = sortingPriority == SortingPriority.NONE;
        inOrder.setVisible(!noPriority);
        orderComboBox.setVisible(!noPriority);
        if (!noPriority){
            orderComboBox.getSelectionModel().select(SortingOrder.REGULAR);
            sortingOrder = SortingOrder.REGULAR;
        }
    }

    private void populateCardView(){
        cardsScroll.setVvalue(0);
        List<ShoppingGridItemController> activeCards = getSelectedProducts();
        List<Node> cards = productPane.getChildren();
        cards.clear();
        for (ShoppingGridItemController controller: activeCards){
            controller.update();
            cards.add(controller);
        }
    }

    private List<ShoppingGridItemController> getSelectedProducts(){
        List<Product> selectedProducts;
        if (parent.searchBarIsEmpty()) {
            selectedProducts = db.getProducts(activeCategory, sortingPriority, sortingOrder);
        } else {
            selectedProducts = db.findProducts(parent.getSearchString(), sortingPriority, sortingOrder);
        }
        List<ShoppingGridItemController> selectedCards = new ArrayList<>();
        for (Product product: selectedProducts){
            selectedCards.add(cardMap.get(product));
        }
        return selectedCards;
    }

    void varukorgUpdated(){
        List<ShoppingGridItemController> selectedProducts = getSelectedProducts();
        for (ShoppingGridItemController controller: selectedProducts){
            controller.update();
        }
    }

    void itemAmountChanged(){
        parent.varukorgUpdated();
    }

    private void updateCategoryMenu(){
        Tree selected = Tree.get(activeCategory);

        if (activeCategory != Category.HOME) {
            List<Tree> siblings = selected.getParent().getChildren();
            for (Tree sibling: siblings){
                if (sibling.hasSubcategory())
                    treeTitledPaneHashMap.get(sibling).setExpanded(false);
            }
             if (selected.hasSubcategory()){
                 treeTitledPaneHashMap.get(selected).setExpanded(true);
            }
        }
        if (selected.hasSubcategory()){
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
        parent.resetSearchBar();
        Tree clicked = controlTreeHashMap.get((Control)event.getSource());
        activeCategory = clicked.getCategory();
        sortingPriorityComboBox.getSelectionModel().select(SortingPriority.NONE);
        update();
    }



}
