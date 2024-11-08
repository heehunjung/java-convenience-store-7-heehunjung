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

    // 필드 변수
    private final List<Product> products;
    private final List<Promotion> promotions;

    // 생성자
    public Store(List<Product> products, List<Promotion> promotions) {
        this.products = products;
        this.promotions = promotions;
    }

    // 공용 메서드
    public void run(Map<String, Integer> shoppingCarts) {
        shoppingCarts.forEach((product, stock) -> {
            Product nomalProduct = findProduct(product);
            Product promotionalProduct = findPromotionProduct(product);

            // 상품 존재 여부 확인
            isProductExists(nomalProduct, promotionalProduct);

            // 유효한 수량 확인
            isValidStock(stock, promotionalProduct, nomalProduct);

            List<Product> purchasedProducts = new ArrayList<Product>();

            // 프로모션 제품 처리
            int promotionalProductNumber = processPromotionalProducts(stock, promotionalProduct, purchasedProducts);

            int remainStock = stock - promotionalProductNumber;

            // 일반 제품 처리
            processNormalProducts(remainStock, nomalProduct, purchasedProducts);
        });
    }

    public int processPromotionalProducts(int stock, Product promotionalProduct, List<Product> purchasedProducts) {
        int purchasePromotionalProductNumber = 0;
        int freeProductNumber = 0;
        int noPromotionalProductNumber = 0;
        int notFreePromotionalProductNumber = 0;

        if (promotionalProduct != null) {
            if (promotionalProduct.checkDate(DateTimes.now())) {
                Integer purchasedStock = buyPromoItemNoDiscount(stock, promotionalProduct, purchasedProducts);
                if (purchasedStock != null) {
                    promotionalProduct.updateStock(purchasedStock);
                    return purchasedStock;
                }

                purchasePromotionalProductNumber = promotionalProduct.getTotalBuyStock(stock);
                freeProductNumber = promotionalProduct.calculateFreeStock(purchasePromotionalProductNumber);

                int promotionalNumber = purchasePromotionalProductNumber + freeProductNumber;

                promotionalProduct.updateStock(promotionalNumber);

                if (promotionalProduct.getStock() > 0) {
                    noPromotionalProductNumber = promotionalProduct.getStock();
                }

                notFreePromotionalProductNumber = noPromotionalProductNumber + purchasePromotionalProductNumber;

                promotionalProduct.updateStock(noPromotionalProductNumber);

                addPurchaseProduct(notFreePromotionalProductNumber, promotionalProduct, purchasedProducts, FALSE);
                addPurchaseProduct(freeProductNumber, promotionalProduct, purchasedProducts, TRUE);
            }
        }
        return notFreePromotionalProductNumber + freeProductNumber;
    }

    public void processNormalProducts(int remainStock, Product nomalProduct, List<Product> purchasedProducts) {
        if (remainStock > 0) {
            nomalProduct.updateStock(remainStock);
            addPurchaseProduct(remainStock, nomalProduct, purchasedProducts, FALSE);
        }
    }

    public Integer buyPromoItemNoDiscount(int stock, Product promotionalProduct, List<Product> purchasedProducts) {
        if (canApplyPromotion(stock, promotionalProduct)) {
            addPurchaseProduct(stock, promotionalProduct, purchasedProducts, FALSE);
            return stock;
        }
        return null;
    }

    public void isProductExists(Product nomalProduct, Product promotionalProduct) {
        if (nomalProduct == null && promotionalProduct == null) {
            throw new IllegalArgumentException(PRODUCT_NOT_FOUND.getMessage());
        }
    }

    public void isValidStock(Integer stock, Product promotionalProduct, Product nomalProduct) {
        int promotionStock = getProductStock(promotionalProduct);
        int normalStock = getProductStock(nomalProduct);

        if (promotionStock + normalStock < stock) {
            throw new IllegalArgumentException(INVALID_INPUT_STOCK.getMessage());
        }
    }

    public Product findProduct(String input) {
        for (Product product : products) {
            if (product.getName().equals(input) && product.getPromotion() == null) {
                return product;
            }
        }
        return null;
    }

    public Product findPromotionProduct(String input) {
        for (Product product : products) {
            if (product.getName().equals(input) && product.getPromotion() != null) {
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

    // private 메서드

    private void addPurchaseProduct(int remainStock, Product nomalProduct, List<Product> purchasedProducts, Boolean isFree) {
        Product purchasedProduct = new Product(nomalProduct, remainStock, FALSE);
        purchasedProducts.add(purchasedProduct);
    }

    private boolean canApplyPromotion(int stock, Product promotionalProduct) {
        return promotionalProduct.getBuyStock() + promotionalProduct.getGetStock() >= stock;
    }

    private static int getProductStock(Product product) {
        int promotionStock = 0;
        if (product != null) {
            promotionStock = product.getStock();
        }
        return promotionStock;
    }
}
