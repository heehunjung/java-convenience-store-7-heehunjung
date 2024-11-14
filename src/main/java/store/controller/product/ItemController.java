package store.controller.product;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;
import store.domain.Stock;
import store.domain.Store;
import store.domain.promotion.Promotion;
import store.utils.Parser;

public class ItemController {

    public void processItems(Stock remainStock, Item purchasedItem, Item nomalItem, List<Item> purchasedItems) {
        int remain = remainStock.getStock();
        if (remain > 0 && nomalItem.getStockCount() > 0) {
            int updateStock = Math.min(remain, nomalItem.getStockCount());
            updateItemStocks(remainStock, purchasedItem, nomalItem, updateStock);
        }
    }

    private void updateItemStocks(Stock remainStock, Item purchasedItem, Item nomalItem, int updateStock) {
        nomalItem.updateStock(updateStock);
        purchasedItem.addStock(updateStock);
        remainStock.minus(updateStock);
    }

    public List<Item> setItems(List<String> information, List<Promotion> promotions) {
        List<Item> items = new ArrayList<>();

        for (String item : information.subList(1, information.size())) {
            List<String> fields = Parser.splitWithCommaDelimiter(item);
            items.add(setItem(fields, promotions));
        }

        return items;
    }

    private Item setItem(List<String> information, List<Promotion> promotions) {
        String name = information.get(0);
        String price = information.get(1);
        String quantity = information.get(2);
        String promotionName = information.get(3);
        Stock stock = new Stock(Parser.stringToInt(quantity));
        Promotion promotion = findPromotionByName(promotionName, promotions);

        return new Item(name, Integer.parseInt(price), stock, promotion);
    }

    private Promotion findPromotionByName(String name, List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        return null;
    }

    public void checkItems(List<Item> items, Store store) {
        List<Item> newItems = new ArrayList<>(items);
        newItems.stream()
                .filter(item -> store.findPromotionProduct(item.getName()) != null && store.findProduct(item.getName()) == null)
                .forEach(item -> store.addItem(
                        new Item(item.getName(), store.findPromotionProduct(item.getName()).getPrice(), new Stock(0), null))
                );
    }
}
