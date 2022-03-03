package rental;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Category {

    private int categoryId;
    private String categoryName;
    private double categoryPrize;

    private StringProperty categoryFieldName = new SimpleStringProperty();

    public Category(int categoryId, String categoryName, double categoryPrize) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryPrize = categoryPrize;
        categoryFieldName.set(categoryName);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getCategoryPrize() {
        return categoryPrize;
    }

    public void setCategoryPrize(double categoryPrize) {
        this.categoryPrize = categoryPrize;
    }

    @Override
    public String toString() {
        return categoryFieldName.get();
    }
}
