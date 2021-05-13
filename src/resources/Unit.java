package resources;

import se.chalmers.cse.dat216.project.Product;

import java.util.HashMap;

public enum Unit {
    KILO("kr/kg", false),
    STYCK("kr/st", true),
    LITER("kr/l", false),
    PÅSE("kr/påse", true),
    FÖRPACKNING("kr/förp", true),
    BURK("kr/burk", true);

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
            case("kg/burk"):
                unit = BURK;        break;
            default:
                throw new IllegalArgumentException();
        }
        map.put(productUnit, unit);
        return unit;
    }
    
    @Override
    public String toString(){ return name; }
    
    public boolean isStyckPris(){ return styckPris; }
    
    public static boolean isStyckPris(Product p){ return get(p).isStyckPris(); }
}
