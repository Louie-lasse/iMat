import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.List;

public class Branch extends Tree{

    private final List<Tree> children = new ArrayList<>();

    public Branch(Category category){
        super(category);
    }

    public void add(Tree tree){
        children.add(tree);
    }

    @Override
    public List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        for (Tree child: children) {
            products.addAll(child.getProducts());
        }
        return products;
    }

    @Override
    public boolean hasSubcategory() {
        return children.size()>0;
    }

    @Override
    public List<Category> getSubcategory() {
        List<Category> subCategories = new ArrayList<>();
        for (Tree child: children){
            subCategories.addAll(child.getSubcategory());
        }
        return subCategories;
    }
}
