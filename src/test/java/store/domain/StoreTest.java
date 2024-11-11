package store.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;
import store.domain.promotion.Range;

public class StoreTest {

    @DisplayName("find_메서드_테스트_01")
    @Test
    void find_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Item item1 = store.findProduct("치킨");
        Item promotionItem1 = store.findPromotionProduct("치킨");

        Assertions.assertThat(item1.getName()).isEqualTo("치킨");
        Assertions.assertThat(promotionItem1.getName()).isEqualTo("치킨");
        Assertions.assertThat(promotionItem1.getPromotion().getName()).isEqualTo("3+1");
    }

    @DisplayName("isValidStock_테스트_01")
    @Test
    void isValidStock_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Item item1 = store.findProduct("치킨");
        Item promotionItem1 = store.findPromotionProduct("치킨");

        Assertions.assertThatThrownBy(() -> store.isValidStock(new Stock(16), item1, promotionItem1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("isValidStock_테스트_01")
    @Test
    void isProductExists_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Item item1 = store.findProduct("치킨");
        Item promotionItem1 = store.findPromotionProduct("치킨");

        Assertions.assertThatThrownBy(() -> store.isProductExists(null,null))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatNoException()
                .isThrownBy(() -> store.isProductExists(item1,null));
        Assertions.assertThatNoException()
                .isThrownBy(() -> store.isProductExists(item1, promotionItem1));
    }

    @DisplayName("checkPromotion_테스트_01")
    @Test
    void checkPromotion_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Item item1 = store.findProduct("치킨");
        Item promotionItem1 = store.findPromotionProduct("치킨");

        Assertions.assertThatThrownBy(() -> store.isProductExists(null,null))
                .isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatNoException()
                .isThrownBy(() -> store.isProductExists(item1,null));
        Assertions.assertThatNoException()
                .isThrownBy(() -> store.isProductExists(item1, promotionItem1));
    }

    @DisplayName("buyPromoItemNoDiscount_테스트_01")
    @Test
    void buyPromoItemNoDiscount_기능_테스트() {
        Store store = createStoreWithTestProductsAndPromotions();

        Item item1 = store.findProduct("치킨");
        Item promotionItem1 = store.findPromotionProduct("치킨");
        List<Item> items = new ArrayList<>();
        Assertions.assertThat(store.buyPromoItemNoDiscount(new Stock(2), promotionItem1, items))
                .isNotNull();
        Assertions.assertThat(store.buyPromoItemNoDiscount(new Stock(5), promotionItem1, items))
                .isNull();
    }

    private Store createStoreWithTestProductsAndPromotions() {
        LocalDate startTime = LocalDate.of(2024, 10, 1);
        LocalDate endTime = LocalDate.of(2024, 12, 30);

        Promotion threePlusOne = new Promotion("3+1", new BuyGet(3, 1), new Range(startTime, endTime));
        Promotion twoPlusOne = new Promotion("2+1", new BuyGet(2, 1), new Range(startTime, endTime));

        Item item1 = new Item("치킨", 5000, new Stock(5), threePlusOne);
        Item item2 = new Item("치킨", 1000, new Stock(10), null);
        Item item3 = new Item("콜라", 2000, new Stock(15), twoPlusOne);
        Item item4 = new Item("사이다", 3000, new Stock(20), null);

        List<Item> items = List.of(item1, item2, item3, item4);
        List<Promotion> promotions = List.of(threePlusOne, twoPlusOne);

        return new Store(items, promotions);
    }
}
