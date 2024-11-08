package store.controller.product;

import static java.lang.Boolean.FALSE;

import java.util.List;
import store.domain.Product;

public class NormalProductController {
    private final Product product;

    public NormalProductController(final Product product) {
        this.product = product;
    }

    public void processProduct(int stock, Product product, List<Product> products) {
        if (stock > 0) {
            product.updateStock(stock);
            addPurchaseProduct(stock, product, products, FALSE);
        }
        // TODO : RETURN 값에 대해 생각
    }
}
