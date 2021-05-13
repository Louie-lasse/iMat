package resources;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.util.*;

public class BackendAdapter{
    private static BackendAdapter adapterInstance = null;
    private IMatDataHandler db;

    private BackendAdapter(){ }

    private void initiateTree(){
        Branch h1 = new Branch(Category.FRUITS_ALL);
        h1.add(new Leaf(Category.FRUIT));
        h1.add(new Leaf(Category.MELONS));
        h1.add(new Leaf(Category.CITRUS_FRUIT));
        Branch h2 = new Branch(Category.BERRIES_EXOTIC_FRUIT);
        h2.add(new Leaf(Category.EXOTIC_FRUIT));
        h2.add(new Leaf(Category.BERRY));
        Branch h3 = new Branch(Category.VEGETABLES);
        h3.add(new Leaf(Category.POD));
        h3.add(new Leaf(Category.CABBAGE));
        h3.add(new Leaf(Category.VEGETABLE_FRUIT));
        Branch fruitsAndVeg = new Branch(Category.FRUITS_AND_GREEN);
        fruitsAndVeg.add(h1);
        fruitsAndVeg.add(h2);
        fruitsAndVeg.add(h3);
        fruitsAndVeg.add(new Leaf(Category.HERB));
        fruitsAndVeg.add(new Leaf(Category.ROOT_VEGETABLE));
        Branch drink = new Branch(Category.DRINKS);
        drink.add(new Leaf(Category.HOT_DRINKS));
        drink.add(new Leaf(Category.COLD_DRINKS));
        Branch pantry = new Branch(Category.PANTRY);
        pantry.add(new Leaf(Category.FLOUR_SUGAR_SALT));
        pantry.add(new Leaf(Category.PASTA));
        pantry.add(new Leaf(Category.POTATO_RICE));
        pantry.add(new Leaf(Category.NUTS_AND_SEEDS));
        Branch breads = new Branch(Category.BREAD_AND_SWEETS);
        breads.add(new Leaf(Category.BREAD));
        breads.add(new Leaf(Category.SWEET));
        Branch meats = new Branch(Category.MEAT_FISH_DAIRIES);
        meats.add(new Leaf(Category.MEAT));
        meats.add(new Leaf(Category.FISH));
        meats.add(new Leaf(Category.DAIRIES));
        Branch home = new Branch(Category.HOME);
        home.add(fruitsAndVeg);
        home.add(drink);
        home.add(pantry);
        home.add(breads);
        home.add(meats);
    }

    public static BackendAdapter getInstance(){
        if (adapterInstance == null) {
            adapterInstance = new BackendAdapter();
            adapterInstance.init();
        }
        return adapterInstance;
    }

    private void init(){
        initiateTree();
        db = IMatDataHandler.getInstance();
    }

    public void shutDown() { db.shutDown(); }

    public boolean isFirstRun() {
        return db.isFirstRun();
    }

    public void resetFirstRun() { db.resetFirstRun(); }

    public boolean isCustomerComplete() { return db.isCustomerComplete(); }

    public void reset() { db.reset(); }

    public Customer getCustomer() {
        return db.getCustomer();
    }

    public User getUser() {
        return db.getUser();
    }

    public CreditCard getCreditCard() {
        return db.getCreditCard();
    }

    public ShoppingCart getShoppingCart() {
        return db.getShoppingCart();
    }

    public Order placeOrder() {
        return db.placeOrder();
    }

    public Order placeOrder(boolean clearShoppingCart) { return db.placeOrder(clearShoppingCart); }

    public List<Order> getOrders() {
        return db.getOrders();
    }

    public Product getProduct(int idNbr) { return db.getProduct(idNbr); }

    /**
     * Returns all the products in the store
     * @return All products in the store
     */
    public List<Product> getAllProducts() {
        return db.getProducts();
    }

    public List<Product> getAllProducts(SortingPriority sp) {
        return getAllProducts(sp, false);
    }

    public List<Product> getAllProducts(SortingPriority sp, boolean reverseOrder){
        List<Product> products = getAllProducts();
        return sort(products, sp, reverseOrder);
    }

    /**
     * Find all products in a certain category
     * @param category A product category
     * @return All Products in the category pc
     */
    public List<Product> getProducts(Category category) {
        return Tree.get(category).getProducts();
    }

    public List<Product> getProducts(Category category, SortingPriority sp){
        return getProducts(category, sp, false);
    }

    public List<Product> getProducts(Category category, SortingPriority sp, boolean reverseOrder) {
        List<Product> products = getProducts(category);
        return sort(products, sp, reverseOrder);
    }

    private List<Product> sort(List<Product> products, SortingPriority sp, boolean reverseOrder){
        Comparator<Product> comparator;
        switch(sp.ordinal()){
            case(0): return products;
            case(1):
                comparator = new PriceComparator();
                break;
            case(2):
                comparator = new EcologicalComparator();
                break;
            default: comparator = new AlphabeticalComparator();
        }
        products.sort(comparator);
        if (reverseOrder) Collections.reverse(products);
        return products;
    }

    public List<Category> getSearchPath(Category c){
        return Tree.get(c).getSearchPath();
    }

    public List<Product> findProducts(String s) { return db.findProducts(s); }

    public void addProduct(Product p) {
        db.addProduct(p);
    }

    public void removeProduct(Product p) {
        db.removeProduct(p);
    }

    public void addFavorite(Product p) { db.addFavorite(p); }

    public void addFavorite(int idNbr) { db.addFavorite(idNbr); }

    public void removeFavorite(Product p) {
        db.removeFavorite(p);
    }

    public boolean isFavorite(Product p) {
        return db.isFavorite(p);
    }

    public List<Product> getFavorites() {
        return db.favorites();
    }

    public Unit getUnit(Product p){
        return Unit.get(p);
    }

    public boolean hasImage(Product p) {
        return db.hasImage(p);
    }

    public Image getFXImage(Product p) { return db.getFXImage(p); }

    public Image getFXImage(Product p, double width, double height) { return db.getFXImage(p, width, height); }

    public Image getFXImage(Product p, double requestedWidth, double requestedHeight, boolean preserveRatio) {
        return db.getFXImage(p, requestedWidth, requestedHeight, preserveRatio);
    }

    public void setProductImage(Product p, File f) { db.setProductImage(p, f); }

    public String imatDirectory() {
        return db.imatDirectory();
    }

    private static class PriceComparator implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            double price1 = o1.getPrice();
            double price2 = o2.getPrice();
            return Double.compare(price1, price2);
        }
    }

    private static class EcologicalComparator implements Comparator<Product>{

        @Override
        public int compare(Product o1, Product o2) {
            if (o1.isEcological() == o2.isEcological()){
                return 0;
            }
            return o1.isEcological()? -1: 1;
        }

    }

    private static class AlphabeticalComparator implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            String name1 = o1.getName();
            String name2 = o2.getName();
            return name1.compareTo(name2);
        }
    }
}
