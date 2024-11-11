package store.controller;

import static store.domain.Store.addPurchaseProduct;
import static store.global.InputConstant.YES_INPUT_BIG;
import static store.utils.Validator.isUserContinuing;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import store.controller.product.ItemController;
import store.controller.product.PromotionItemController;
import store.domain.Item;
import store.domain.Receipt;
import store.domain.Stock;
import store.domain.Store;
import store.domain.promotion.Promotion;
import store.utils.Parser;
import store.utils.Validator;
import store.view.FileInput;
import store.view.InputView;
import store.view.OutputView;

public class FrontController {

    private final PromotionItemController promotionItemController;
    private final ItemController itemController;
    private final PromotionController promotionController;

    public FrontController(ItemController itemController, PromotionItemController promotionItemController,
                           PromotionController promotionController1) {
        this.promotionItemController = promotionItemController;
        this.itemController = itemController;
        this.promotionController = promotionController1;
    }

    //TODO : IOException from getLines -> Main 에서 처리
    public void run() throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Promotion> promotions = getPromotions();
        List<Item> items = getItems(promotions);

        Store store = new Store(items, promotions);
        String isContinue = YES_INPUT_BIG;

        itemController.checkItems(items,store);

        while(isUserContinuing(isContinue)) {
            isContinue = processPurchaseInStore(store, scanner);
        }
    }

    private String processPurchaseInStore(Store store, Scanner scanner) {
        String isContinue;
        printStoreInfo(store);

        Map<String, Stock> itemAndStock = getStringStockMap(scanner, store);

        String membershipInput = getMembership(scanner);

        List<Item> purchasedItems = buyProcess(itemAndStock, store);

        Receipt receipt = new Receipt(purchasedItems, false);
        receiptMagic(store, scanner, receipt, membershipInput);

        return isContinue(scanner);
    }

    private String isContinue(Scanner scanner) {
        String isContinue;
        isContinue = InputView.getEndingMessage(scanner);
        Validator.YesOrNoValidator(isContinue);
        return isContinue;
    }

    private void receiptMagic(Store store, Scanner scanner, Receipt receipt, String membershipInput) {
        receipt.notifyStockForFree(store, scanner);
        receipt.notifyPurchasedInfo(store, scanner);
        receipt.calculatePrice();

        if (isUserContinuing(membershipInput)) {
            receipt.checkMembership();
        }
        OutputView.printReceipt(receipt);
    }

    private void printStoreInfo(Store store) {
        OutputView.printGreeting();
        OutputView.printStoreInformation(store);
    }

    private List<Promotion> getPromotions() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.PROMOTION_FILE_NAME);
        List<String> promotionStrings = InputView.getLines(br);
        return promotionController.setPromotions(promotionStrings);
    }

    private List<Item> getItems(List<Promotion> promotions) throws IOException {
        BufferedReader br;
        br = FileInput.FileInputSetting(FileInput.ITEM_FILE_NAME);
        List<String> itemStrings = InputView.getLines(br);
        return itemController.setItems(itemStrings, promotions);
    }

    private Map<String, Stock> getStringStockMap(Scanner scanner,Store store) {
        try {
            String itemInput = InputView.getBuyProductMessage(scanner);
            Validator.buyInputFormatValidator(itemInput);
            Map<String, Stock> itemAndStock = getStringStockMap(itemInput);
            duplicateNameValidate(itemAndStock);
            invalidStockValidator(itemAndStock, store);
            return itemAndStock;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getStringStockMap(scanner,store);
        }
    }

    private void invalidStockValidator(Map<String, Stock> itemAndStock,Store store) {
        itemAndStock.forEach((name,stock) ->{
            Item item = store.findProduct(name);
            Item promotionItem = store.findPromotionProduct(name);
            store.isValidStock(stock,item,promotionItem);
                }
                );
    }
    private void duplicateNameValidate(Map<String, Stock> itemAndStock) {
        List<String> names = new ArrayList<>(itemAndStock.keySet());
        Validator.duplicatedNameValidator(names);
    }

    private Map<String, Stock> getStringStockMap(String itemInput) {
        List<String> splitItemInput = Parser.splitWithCommaDelimiter(itemInput);
        Map<String, Stock> itemAndStock = new HashMap<>();
        for (String itemInfo : splitItemInput) {
            Parser.itemAndStockParser(itemInfo, itemAndStock);
        }
        return itemAndStock;
    }

    private String getMembership(Scanner scanner) {
        try {
            String membershipInput = InputView.getMembershipMessage(scanner);
            Validator.YesOrNoValidator(membershipInput);
            return membershipInput;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getMembership(scanner);
        }
    }

    public List<Item> buyProcess(Map<String, Stock> shoppingCarts, Store store) {
        List<Item> purchasedItems = new ArrayList<>();

        shoppingCarts.forEach((product, stock) -> {
            Item nomalItem = store.findProduct(product);
            Item promotionalItem = store.findPromotionProduct(product);
            // 상품 존재 여부 확인
            store.isProductExists(nomalItem, promotionalItem);

            // 유효한 수량 확인
            store.isValidStock(stock, promotionalItem, nomalItem);

            // 프로모션 제품 처리
            Item item = promotionItemController.processPromotionItem(stock, promotionalItem, purchasedItems,
                    store);

            if (item == null) {
                item = new Item(nomalItem, new Stock(0), Boolean.FALSE);
            }
            // 일반 제품 처리
            itemController.processProducts(stock, item, nomalItem, purchasedItems);

            addPurchaseProduct(purchasedItems,item);

        });

        return purchasedItems;
    }

}
