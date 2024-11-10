package store.controller.product;

import static java.lang.Boolean.FALSE;
import static store.domain.Store.addPurchaseProduct;

import java.util.ArrayList;
import java.util.List;
import store.domain.Item;
import store.domain.Stock;
import store.domain.promotion.Promotion;
import store.utils.Parser;

public class ItemController {

    public void processProducts(Stock remainStock, Item nomalItem, List<Item> purchasedItems) {
        if (remainStock.getStock() > 0) {
            // todo : 흐름 상 nomalItem.getStockCount()가 무조건 remainStock 보다 크긴 한대 검증 해야함?
            nomalItem.updateStock(remainStock.getStock());
            addPurchaseProduct(remainStock, nomalItem, purchasedItems, FALSE);
        }
    }

    public List<Item> setItems(List<String> information,List<Promotion> promotions) {
        List<Item> items = new ArrayList<Item>();

        for (String item : information.subList(1, information.size())) {
            List<String> fields = Parser.splitWithCommaDelimiter(item);
            items.add(setItem(fields, promotions));
        }

        return items;
    }

    private Item setItem(List<String> information,List<Promotion> promotions) {
        String name = information.get(0);
        String price = information.get(1);
        String quantity = information.get(2);
        String promotionName = information.get(3);

        Stock stock = new Stock(Parser.stringToInt(quantity));
        Promotion promotion = findPromotionByName(promotionName, promotions);

        return new Item(name, Integer.parseInt(price), stock, promotion);
    }

    private Promotion findPromotionByName(String name,List<Promotion> promotions) {
        for (Promotion promotion : promotions) {
            if (promotion.getName().equals(name)) {
                return promotion;
            }
        }
        return null;
    }}
