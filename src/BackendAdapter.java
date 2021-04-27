import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.util.*;

public class BackendAdapter{
    private static BackendAdapter adapterInstance = null;
    private final IMatDataHandler db = IMatDataHandler.getInstance();
    private final HashMap<Category, Tree> treeMap = new HashMap<>();

    private BackendAdapter(){
        initiateTree();
    }

    private void initiateTree(){
        //TODO add all branches (custom master category) and leaves (regular categories) to the tree to find them
    }

    public static BackendAdapter getInstance(){
        if (adapterInstance == null) {
            adapterInstance = new BackendAdapter();
        }
        return adapterInstance;
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

    /**
     * Find all products in a certain category
     * @param pc A product category
     * @return All Products in the category pc
     */
    public List<Product> getProducts(Category pc) {
        return treeMap.get(pc).getProducts();
    }

    public List<Product> getProducts(Category pc, SortingPriority sp){
        return getProducts(pc, sp, false);
    }

    public List<Product> getProducts(Category pc, SortingPriority sp, boolean reverseOrder) {
        List<Product> products = getProducts(pc);
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

    public List<Product> favorites() {
        return db.favorites();
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
