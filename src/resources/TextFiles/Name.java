package resources.TextFiles;

import java.util.Random;

public class Name {
    private static final Random rand = new Random();
    private static final String[] names = {"Emma",
    "Sara",
    "Elin",
    "Amanda",
    "Hanna",
    "Johanna",
    "Julia",
    "Emelie",
    "Josefin",
    "Anna",
    "Marcus",
    "Erik",
    "Simon",
    "Alexander",
    "Viktor",
    "Oskar",
    "Johan",
    "Daniel",
    "Filip",
    "Anton"};

    public static String get(){
        int random =  rand.nextInt(names.length);
        return names[random];
    }
}
