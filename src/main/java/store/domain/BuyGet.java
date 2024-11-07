package store.domain;

public class BuyGet {
    private final int getCount;
    private final int buyCount;

    BuyGet(int buyCount, int getCount) {
        this.getCount = getCount;
        this.buyCount = buyCount;
    }

    public int calculateBuyStock(int totalStock) {
        int setCount = getCount + buyCount;
        int getCount = totalStock/setCount;

        return getCount*buyCount;
    }
}
