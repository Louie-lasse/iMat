package resources;

public enum SortingOrder {
    REGULAR("vanlig ordning"),
    REVERSE("motsatt ordning");

    private final String name;

    SortingOrder(String s){
        name = s;
    }

    @Override
    public String toString(){
        return name;
    }
}
