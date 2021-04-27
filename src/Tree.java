import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.util.List;

public abstract class Tree {
    /**
     * Create a tree with categories and subcategories. Each part is attributed a Category (custom)
     * If the category exists in IMatDataHandler it has an attributed ProductCategory (and should be a leaf),
     * Else, it should be a branch, with it's own leaves. A branch can be part of multiple categories
     */
    protected static final IMatDataHandler db = IMatDataHandler.getInstance();
    protected final Category category;

    protected Tree(Category category) {
        this.category = category;
    }

    public abstract List<Product> getProducts();
    public abstract boolean hasSubcategory();
    public Category getCategory(){
        return this.category;
    }
    public abstract List<Category> getSubcategory();
}
