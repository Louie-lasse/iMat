package resources;

import se.chalmers.cse.dat216.project.ProductCategory;

public enum Category {
    POD("Baljväxter", ProductCategory.POD),
    BREAD("Bröd", ProductCategory.BREAD),
    BERRY("Bär", ProductCategory.BERRY),
    CITRUS_FRUIT("Citrusfrukter", ProductCategory.CITRUS_FRUIT),
    HOT_DRINKS("Varma drycker", ProductCategory.HOT_DRINKS),
    COLD_DRINKS("Kalla drycker", ProductCategory.COLD_DRINKS),
    EXOTIC_FRUIT("Exotiska frukter", ProductCategory.EXOTIC_FRUIT),
    FISH("Fisk", ProductCategory.FISH),
    VEGETABLE_FRUIT("Grönsaker", ProductCategory.VEGETABLE_FRUIT),
    CABBAGE("Kål", ProductCategory.CABBAGE),
    MEAT("Kött", ProductCategory.MEAT),
    DAIRIES("Mejeriprodukter", ProductCategory.DAIRIES),
    MELONS("Melon", ProductCategory.MELONS),
    FLOUR_SUGAR_SALT("Mjöl, socker, salt", ProductCategory.FLOUR_SUGAR_SALT),
    NUTS_AND_SEEDS("Nötter och frön", ProductCategory.NUTS_AND_SEEDS),
    PASTA("Pasta", ProductCategory.PASTA),
    POTATO_RICE("Potatis, ris", ProductCategory.POTATO_RICE),
    ROOT_VEGETABLE("Rotfrukter", ProductCategory.ROOT_VEGETABLE),
    FRUIT("Frukt", ProductCategory.FRUIT),
    SWEET("Sötsaker", ProductCategory.SWEET),
    HERB("Örter", ProductCategory.HERB),
    FRUITS_ALL("Frukter"),
    BERRIES_EXOTIC_FRUIT("Bär och exotiska frukter"),
    VEGETABLES("Grönsaker"),
    FRUITS_AND_GREEN("Frukt och grönt"),
    DRINKS("Drycker"),
    MEAT_FISH_DAIRIES("Kött, fisk, och mejeri"),
    BREAD_AND_SWEETS("Bröd, fikabröd och sötsaker"),
    PANTRY("Skafferi"),
    HOME("Hem");

    private final String name;

    private ProductCategory productCategory;

    Category(String s){
        this.name = s;
    }

    Category(String s, ProductCategory pc){
        name = s;
        productCategory = pc;
    }

    @Override
    public String toString(){
        return name;
    }

    public ProductCategory getProductCategory(){
        return productCategory;
    }
}
