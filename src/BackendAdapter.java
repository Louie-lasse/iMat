import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.util.List;

public class BackendAdapter extends IMatDataHandler{
    private static BackendAdapter adapterInstance = null;
    private final IMatDataHandler db = IMatDataHandler.getInstance();

    private BackendAdapter(){ }

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

    public List<Product> getProducts() {
        return db.getProducts();
    }

    public List<Product> getProducts(ProductCategory pc) { return db.getProducts(pc); }

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



}
