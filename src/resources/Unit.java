package resources;

import se.chalmers.cse.dat216.project.Product;

import java.util.HashMap;

public enum Unit {
    KILO("kg", false),
    STYCK("st", true),
    LITER("l", false),
    PÅSE("påse", true),
    FÖRPACKNING("förp", true),
    BURK("burk", true);

    private static HashMap<String, Unit> map = new HashMap<>();

    private final String name;

    private final boolean styckPris;

    Unit(String s, boolean b){
        name = s;
        styckPris = b;
    }

    public static Unit get(Product p){
        String productUnit = p.getUnit().toLowerCase();
        Unit u = map.get(productUnit);
        if (u != null) return u;
        Unit unit;
        switch (productUnit){
            case("kr/kg"):
                unit = KILO;        break;
            case("kr/st"):
                unit = STYCK;       break;
            case("kr/l"):
                unit = LITER;       break;
            case("kr/påse"):
                unit = PÅSE;        break;
            case("kr/förp"):
                unit = FÖRPACKNING; break;
            case("kr/burk"):
                unit = BURK;        break;
            default:
                System.out.println("Illegal product" + p + " with id " + p.getProductId());
                throw new IllegalArgumentException();
        }
        map.put(productUnit, unit);
        return unit;
    }
    
    @Override
    public String toString(){
        return "kr/"+name;
    }

    public String type(){
        return name;
    }
    
    public boolean isStyckPris(){ return styckPris; }
    
    public static boolean isStyckPris(Product p){ return get(p).isStyckPris(); }
}
