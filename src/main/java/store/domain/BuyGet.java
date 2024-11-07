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

        if (totalStock <= setCount) {
            return -1;
            // todo : 에러 처리할지, 그냥 무시할지 외부에서 더 큰 것만 보내긴 할 것
        }

        int getCount = totalStock/setCount;

        return getCount*buyCount;
    }
}
