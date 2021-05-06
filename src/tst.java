import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

public class tst{
    public static void main(String[] args) {
        Product p1 = IMatDataHandler.getInstance().getProduct(87);  //kr/kg
        Product p2 = IMatDataHandler.getInstance().getProduct(92);  //kr/förp   FINNS VIKT???
        Product p3 = IMatDataHandler.getInstance().getProduct(77);  //kr/st     FINNS VIKT???
        Product p4 = IMatDataHandler.getInstance().getProduct(111);  //kr/st     FINNS VIKT???
        int x = 5;
        //magnus kommentar
    }
}