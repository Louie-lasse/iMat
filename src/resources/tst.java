package resources;

import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.util.List;

public class tst{
    public static void main(String[] args) {
        BackendAdapter.getInstance().getFormattedAmount(BackendAdapter.getInstance().getProduct(130));
    }
}