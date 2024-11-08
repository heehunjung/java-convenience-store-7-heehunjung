package store.domain;


import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static store.global.ErrorMessages.INVALID_INPUT_STOCK;
import static store.global.ErrorMessages.PRODUCT_NOT_FOUND;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.promotion.Promotion;

public class Store {
    private final List<Product> products;
    private final List<Promotion> promotions;

    public Store(List<Product> products, List<Promotion> promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    public void run(Map<String,Integer> shoppingCarts) {
        shoppingCarts.forEach((product,stock)->{
            Product nomalProduct = findProduct(product);
            Product promotionalProduct = findPromotionProduct(product);

            //TODO : TRY-CATCH 추가
            // 상품 존재
            //메서드 1 ====================================================
            isProductExists(nomalProduct, promotionalProduct);
            //============================================================
            //TODO : TRY-CATCH 추가
            // 수량 있음
            //메서드 2 ====================================================
            isValidStock(stock, promotionalProduct, nomalProduct);
            // ==========================================================


            List<Product> purchasedProducts = new ArrayList<Product>();

            // 메서드3 ====================================================
            int purchasePromotionalProductNumber = 0 ;
            int noPromotionalProductNumber = 0 ;
            int notFreePromotionalProductNumber = 0 ;
            int freeProductNumber = 0 ;
            int promotionalProductNumber = 0 ;
            if (promotionalProduct != null ) {
                if (promotionalProduct.getPromotion().checkDate(DateTimes.now())) {
                    // TODO : 여기 프로모션 구매 검증 들어가야함
                    purchasePromotionalProductNumber = promotionalProduct.getPromotion().getTotalBuyStock(stock);
                    freeProductNumber = promotionalProduct.getPromotion().calculateFreeStock(purchasePromotionalProductNumber);

                    if (stock > 0 && promotionalProduct.getStock() > 0 ) {
                        noPromotionalProductNumber = promotionalProduct.getStock();
                    }

                    notFreePromotionalProductNumber = noPromotionalProductNumber + purchasePromotionalProductNumber;
                    promotionalProductNumber = notFreePromotionalProductNumber + freeProductNumber;

                    promotionalProduct.updateStock(promotionalProductNumber);

                    Product purchasedProduct = new Product(promotionalProduct,notFreePromotionalProductNumber, FALSE);
                    Product freeProduct = new Product(promotionalProduct,freeProductNumber, TRUE);
                    purchasedProducts.add(purchasedProduct);
                    purchasedProducts.add(freeProduct);
                }
            }
            // ===================================================================

            int remainStock = stock - promotionalProductNumber;

            // 메서드 4 ===========================================================
            if (remainStock > 0) {
                // TODO : NULL  인 경우 ? 내 생각엔 없는데,
                nomalProduct.updateStock(remainStock);
                Product purchasedProduct = new Product(nomalProduct,remainStock, FALSE);
                purchasedProducts.add(purchasedProduct);
            }
            // ===================================================================
        });

    }

    private static void isProductExists(Product nomalProduct, Product promotionalProduct) {
        if (nomalProduct ==  null && promotionalProduct == null) {
            throw new IllegalStateException(PRODUCT_NOT_FOUND.getMessage());
        }
    }

    // TODO : 메서드 10줄 이상
    private static void isValidStock(Integer stock, Product promotionalProduct, Product nomalProduct) {
        int promotionStock = 0 ;
        int normalStock = 0 ;
        if (promotionalProduct != null) {
            promotionStock = promotionalProduct.getStock();
        }
        if (nomalProduct != null) {
            normalStock = nomalProduct.getStock();
        }

        if (promotionStock+normalStock < stock) {
            throw new IllegalStateException(INVALID_INPUT_STOCK.getMessage());
        }
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

    public List<Product> getProducts() {
        return products;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }
}
