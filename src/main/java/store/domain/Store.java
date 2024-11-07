package store.domain;

import java.util.List;

public class Store {
    private final List<Product> products;
    private final List<Promotion> promotions;

    public Store(List<Product> products, List<Promotion> promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public Product findProduct(String input) {
        for (Product product : products) {
            if (product.getName().equals(input) && product.getPromotion() == null ) {
                return product;
            }
        }
        return null;
    }

    public Product findPromotionProduct(String input) {
        for (Product product : products) {
            if (product.getName().equals(input) && product.getPromotion() != null ) {
                return product;
            }
        }
        return null;
    }
}
