package store.domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import store.domain.promotion.BuyGet;
import store.domain.promotion.Promotion;

public class ReceiptTest {

    @DisplayName("canUseMembership_테스트_01")
    @Test
    void canUseMembership_기능_테스트() {
        List<Item> items = getProducts();

        Receipt receipt = new Receipt(items, true);

        Assertions.assertThat(receipt.getMembership())
                .isTrue();
    }

    @DisplayName("금액_계산_테스트_01")
    @Test
    void calculatePrice_기능_테스트() {
        List<Item> items = getProducts();

        Receipt receipt = new Receipt(items, true);
        receipt.calculatePrice();

        Assertions.assertThat(receipt.getTotalPrice())
                .isEqualTo(69000);
        Assertions.assertThat(receipt.getPromotionPrice())
                .isEqualTo(0);
    }

    @DisplayName("멤버쉽_적용_테스트_01")
    @Test
    void applyMembership_기능_테스트() {
        List<Item> items = getProducts();

        Receipt receipt = new Receipt(items, true);
        receipt.calculatePrice();
        receipt.applyMembership();

        Assertions.assertThat(receipt.getMembershipPrice())
                .isEqualTo(8000);
    }

    private List<Item> getProducts() {
        Promotion promotion = new Promotion("test", new BuyGet(3,1) , null);

        Item item1 = new Item("치킨", 5000, new Stock(10), promotion);
        Item item2 = new Item("치킨", 1000, new Stock(10), null);
        Item item3 = new Item("콜라", 2000, new Stock(15), promotion);
        Item item4 = new Item("사이다", 3000, new Stock(20), null);

        Item purchased1 = new Item(item1, new Stock(5), Boolean.FALSE);
        Item purchased2 = new Item(item2, new Stock(6), Boolean.FALSE);
        Item purchased3 = new Item(item3, new Stock(7), Boolean.FALSE);
        Item purchased4 = new Item(item4, new Stock(8), Boolean.FALSE);

        return List.of(purchased1, purchased2, purchased3, purchased4);
    }
}
