package resources;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;

import static resources.SortingOrder.REGULAR;
import static resources.SortingOrder.REVERSE;

public class BackendAdapter{
    private static final BackendAdapter adapterInstance = new BackendAdapter();
    private static final IMatDataHandler db = IMatDataHandler.getInstance();
    private static final ShoppingCart cart = db.getShoppingCart();
    private static final CreditCard card = db.getCreditCard();

    private BackendAdapter(){
        init();
    }

    public static CreditCard getCard() {
        return card;
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

    public boolean isValidTime(String hour, String min){
        LocalTime localTime = LocalTime.now();
        int hourNow = Integer.parseInt(localTime.toString().substring(0,2));
        int minNow = Integer.parseInt(localTime.toString().substring(3,5));

        int hourEntred = Integer.parseInt(hour);
        int minEntred = Integer.parseInt(min);
        if(hourEntred > hourNow){
            return true;
        }else if(hourEntred == hourNow && minEntred-minNow >= 30){
            return true;
        }
        return false;
    }
    public boolean isValidDate(LocalDate date){
        LocalDateTime now = LocalDateTime.now();
        if(date.isAfter(ChronoLocalDate.from(now))){
            return true;
        }else{
            return false;
        }
    }
    public boolean isValidName(String s){
        if (s.length()==0) return false;
        char[] chars = s.toCharArray();
        for (char c: chars){
            if (!(Character.isLetter(c) || c==' ' || c=='.' || c=='-')) return false;
        }
        return true;
    }

    public boolean isValidNumber(String s){
        s = removeRedundantCharsNumber(s);
        if (s.charAt(0) == '+'){
            if (s.length()<10 || s.length() >12) return false;
            char[] chars = s.toCharArray();
            for (int i = 1; i < chars.length; i++){
                if (!Character.isDigit(chars[i])) return false;
            }
        } else {
            if (s.length()<8 || s.length() >10) return false;
            char[] chars = s.toCharArray();
            for (char c: chars){
                if (!Character.isDigit(c)) return false;
            }
        }
        return true;
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
        char[] chars = s.toCharArray();
        boolean containsNumber = false;
        for (char c: chars){
            if (!Character.isLetter(c)){
                if (Character.isDigit(c)){
                    containsNumber = true;
                } else if (!(c==' ' || c=='.' || c==',' || (c=='-'))){
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
    private String date;
    private String time;

    public void setDate(String s){
        this.date = s;
    }

    public void setTime(String s){
        this.time = s;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public void clearCart(){
        cart.clear();
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
        return getAllProducts(sp, REGULAR);
    }

    public List<Product> getAllProducts(SortingPriority sp, SortingOrder order){
        List<Product> products = getAllProducts();
        return sort(products, sp, order);
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
        return getProducts(category, sp, REGULAR);
    }

    public List<Product> getProducts(Category category, SortingPriority sp, SortingOrder order) {
        List<Product> products = getProducts(category);
        return sort(products, sp, order);
    }

    private List<Product> sort(List<Product> products, SortingPriority sp, SortingOrder order){
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
        if (order == REVERSE) Collections.reverse(products);
        return products;
    }

    public List<Category> getSearchPath(Category c){
        return Tree.get(c).getSearchPath();
    }

    public List<Product> findProducts(String s) { return db.findProducts(s); }

    public void addProduct(Product p) {
        if (Unit.isStyckPris(p)){
            addProduct(p, 1);
        } else{
            addProduct(p, 0.1);
        }
    }

    private void addProduct(Product p, double amount){
        try{
            ShoppingItem item = getShoppingItem(p);
            item.setAmount(item.getAmount()+amount);
        } catch (IllegalArgumentException ignored){
            cart.addProduct(p, amount);
        }
    }

    public void subtractProduct(Product p) {
        if (Unit.isStyckPris(p)){
            subtractProduct(p, 1);
        } else{
            subtractProduct(p, 0.1);
        }
    }

    private void subtractProduct(Product p, double amount){
        try{
            ShoppingItem item = getShoppingItem(p);
            double oldAmount = item.getAmount();
            if (oldAmount <= amount){
                cart.removeItem(item);
            } else {
                item.setAmount(oldAmount-amount);
            }
        } catch (IllegalArgumentException ignored){

        }
    }

    public void removeProduct(Product p){
        try{
            cart.removeItem(getShoppingItem(p));
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

    public String getFormattedAmount(Product p){
        double amount = getAmount(p);
        Unit unit = Unit.get(p);
        if (unit.isStyckPris()){
            return (int) amount +" "+ unit.type();
        } else {
            return  (double)Math.round(amount*10)/10 +" "+ unit.type();
        }
    }

    public double getTotalPrice(){
        return cart.getTotal();
    }

    public double getPriceOrder(Order order){
        int i = getOrders().indexOf(order);
        double price = 0;
        for(ShoppingItem items : getOrders().get(i).getItems()){
            price += items.getTotal();
        }
        return price;
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
