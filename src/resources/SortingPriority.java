package resources;

public enum SortingPriority {
    NONE("inte"),
    PRICE("efter pris"),
    ECOLOGICAL("efter ekologisk"),
    ALPHABETICAL("efter alfabetet");

    private final String name;

    SortingPriority(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
