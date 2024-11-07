package store.domain;

public class BuyGet {
    private final int getStock;
    private final int buyStock;

    BuyGet(int buyCount, int getCount) {
        this.getStock = getCount;
        this.buyStock = buyCount;
    }

    public int calculateBuyStock(int totalStock) {
        int setCount = getStock + buyStock;
        int getCount = totalStock/setCount;

        return getCount* buyStock;
    }

    public int calculateGetStock(int buyStock) {
       int count = buyStock/this.buyStock;
       return count * this.getStock;
    }

    public boolean isPromotionAvailable(int input) {
        return input <= buyStock;
    }

    public boolean isPromotionAvailable(int input) {
        return input <= buyCount;
    }
}
