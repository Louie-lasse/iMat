package resources;

import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.List;

public class Leaf extends Tree{

    public Leaf(Category category) {
        super(category);
    }

    @Override
    public List<Product> getProducts() {
        return db.getProducts(category.getProductCategory());
    }

    @Override
    public boolean hasSubcategory() {
        return false;
    }

    @Override
    public List<Tree> getChildren() {
        return new ArrayList<>();
    }

    @Override
    public List<Category> getSubcategory() {
        return new ArrayList<>();
    }
}
