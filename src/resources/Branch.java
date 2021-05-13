package resources;

import se.chalmers.cse.dat216.project.Product;

import java.util.ArrayList;
import java.util.List;

public class Branch extends Tree{

    private final List<Tree> children = new ArrayList<>();

    public Branch(Category category){
        super(category);
    }

    public void add(List<Tree> trees){
        for (Tree tree: trees) add(tree);
    }

    public void add(Tree tree){
        tree.setParent(this);
        this.children.add(tree);
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
    public List<Tree> getChildren() {
        return children;
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
