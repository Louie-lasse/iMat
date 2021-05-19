package resources;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.util.*;

public class BackendAdapter{
    private static final BackendAdapter adapterInstance = new BackendAdapter();
    private static final IMatDataHandler db = IMatDataHandler.getInstance();
    private static final ShoppingCart cart = db.getShoppingCart();

    private BackendAdapter(){
        init();
    }

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
        pantry.add(new Leaf(Category.RICE));
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
        return adapterInstance;
    }

    private void init(){
        initiateTree();
    }

    public void shutDown() { db.shutDown(); }

    public boolean isFirstRun() {
        return db.isFirstRun();
    }

    public void resetFirstRun() { db.resetFirstRun(); }

    public boolean isCustomerComplete() { return db.isCustomerComplete(); }

    public boolean isValidName(String s){
        if (s.length()==0) return false;
        char[] chars = s.toCharArray();
        for (char c: chars){
            if (Character.isDigit(c)) return false;
        }
        return true;
    }

    public boolean isValidNumber(String s){
        s = removeRedundantCharsNumber(s);
        if (!(s.length()==10) && !(s.length()==12)) return false;
        return s.length() != 12 || s.charAt(0) == '+';
    }

    private String removeRedundantCharsNumber(String s){
        final List<Character> removableChars = new ArrayList<>();
        removableChars.add(' '); removableChars.add('.'); removableChars.add('-');
        char[] chars = s.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c: chars){
            if (!removableChars.contains(c)) builder.append(c);
        }
        return builder.toString();
    }

    public boolean isValidEmail(String s){
        if (containsIllegalEmailChars(s)) return false;
        if (s.indexOf('@')<1) return false;
        return s.indexOf('.', s.indexOf('@')) >= 1 && s.indexOf('.', s.indexOf('@')) != s.length() - 1;
    }

    private boolean containsIllegalEmailChars(String s){
        char[] chars = s.toCharArray();
        for (char c: chars){
            if (!Character.isLetter(c)){
                if(!Character.isDigit(c)) {
                    if (!(c == '.')) {
                        if (!(c == '@')) {
                            if (!(c == '-')) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isValidAddress(String s){
        boolean containsNumber = false;
        for (int i = 0; i < 10; i++){
            if (s.contains(Integer.toString(i))){
                containsNumber = true;
                if (s.indexOf(i) < 2){
                    return false;
                }
            }
        }
        return containsNumber;
    }

    public boolean isValidZip(String s){
        s = removeRedundantCharsNumber(s);
        if (s.length() != 5) return false;
        char[] chars = s.toCharArray();
        for(char c: chars){
            if (!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public boolean isValidCity(String s){
        return isValidName(s);
    }

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
        try{
            ShoppingItem item = getShoppingItem(p);
            item.setAmount(item.getAmount()+1);
        } catch (IllegalArgumentException ignored){
            cart.addProduct(p, 1);
        }
    }

    public void removeProduct(Product p) {
        try{
            ShoppingItem item = getShoppingItem(p);
            double amount = item.getAmount();
            if (amount <= 1){
                cart.removeItem(item);
            } else {
                item.setAmount(amount-1);
            }
        } catch (IllegalArgumentException ignored){

        }
    }

    public double getAmount(Product p){
        try{
            return getShoppingItem(p).getAmount();
        } catch (IllegalArgumentException exception){
            return 0;
        }
    }

    public double getTotalPrice(){
        return cart.getTotal();
    }

    public double getShoppingItemTotal(Product product){
        try{
            return getShoppingItem(product).getTotal();
        } catch (IllegalArgumentException exception){
            return 0;
        }
    }

    private ShoppingItem getShoppingItem(Product p) throws IllegalArgumentException{
        List<ShoppingItem> cartItems = cart.getItems();
        for (ShoppingItem item: cartItems){
            if (item.getProduct().equals(p)){
                return item;
            }
        }
        throw new IllegalArgumentException();
    }

    public void setProductAmount(Product p, Double amount){
        if (amount < 0) throw new IllegalArgumentException();
        if (amount == 0) return;
        try{
            ShoppingItem item = getShoppingItem(p);
            item.setAmount(amount);
        } catch (IllegalArgumentException exception){
            cart.addProduct(p, amount);
        }
    }

    public List<Product> getCartProducts(){
        List<ShoppingItem> items = cart.getItems();
        List<Product> products = new ArrayList<>();
        for (ShoppingItem item: items){
            products.add(item.getProduct());
        }
        return products;
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
