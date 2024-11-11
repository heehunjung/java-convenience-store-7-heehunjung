package store.controller;

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
    private StoreController storeController;

    public FrontController(ItemController itemController, PromotionItemController promotionItemController,
                           PromotionController promotionController) {
        this.promotionItemController = promotionItemController;
        this.itemController = itemController;
        this.promotionController = promotionController;
    }

    public void run() throws IOException {
        Store store = initializeStore();
        storeController = new StoreController(itemController, store, promotionItemController);
        Scanner scanner = new Scanner(System.in);

        String isContinue = YES_INPUT_BIG;
        itemController.checkItems(store.getItems(), store);

        while (isUserContinuing(isContinue)) {
            isContinue = processPurchaseInStore(store, scanner);
        }
    }

    private Store initializeStore() throws IOException {
        List<Promotion> promotions = getPromotions();
        List<Item> items = getItems(promotions);
        return new Store(items, promotions);
    }

    private String processPurchaseInStore(Store store, Scanner scanner) {
        printStoreInfo(store);

        Map<String, Stock> itemAndStock = getStringStockMap(scanner, store);

        String membershipInput = getMembership(scanner);

        List<Item> purchasedItems = storeController.buyProcess(itemAndStock);

        Receipt receipt = new Receipt(purchasedItems, false);
        receiptMagic(store, scanner, receipt, membershipInput);

        return isContinue(scanner);
    }

    private String isContinue(Scanner scanner) {
        try {
            String isContinue;
            isContinue = InputView.getEndingMessage(scanner);
            Validator.YesOrNoValidator(isContinue);
            return isContinue;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return isContinue(scanner);
        }
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
        BufferedReader br = FileInput.FileInputSetting(FileInput.ITEM_FILE_NAME);
        List<String> itemStrings = InputView.getLines(br);
        return itemController.setItems(itemStrings, promotions);
    }

    private Map<String, Stock> getStringStockMap(Scanner scanner, Store store) {
        try {
            String itemInput = InputView.getBuyProductMessage(scanner);
            Validator.buyInputFormatValidator(itemInput);
            Map<String, Stock> itemAndStock = getStringStockMap(itemInput);

            invalidStockValidator(itemAndStock, store);
            return itemAndStock;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getStringStockMap(scanner, store);
        }
    }

    private void invalidStockValidator(Map<String, Stock> itemAndStock, Store store) {
        itemAndStock.forEach((name, stock) -> {
            Item item = store.findProduct(name);
            Item promotionItem = store.findPromotionProduct(name);
            store.isValidStock(stock, item, promotionItem);
        });
    }

    private Map<String, Stock> getStringStockMap(String itemInput) {
        List<String> splitItemInput = Parser.splitWithCommaDelimiter(itemInput);
        List<String> itemNames = new ArrayList<>();
        Map<String, Stock> itemAndStock = new HashMap<>();

        for (String itemInfo : splitItemInput) {
            Parser.itemAndStockParser(itemInfo, itemAndStock);
            itemNames.add(Parser.splitWithBarDelimiter(itemInfo).getFirst());
        }

        Validator.duplicatedNameValidator(itemNames);
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

}
