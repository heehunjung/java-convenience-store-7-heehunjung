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
    private ReceiptController receiptController;

    public FrontController(ItemController itemController, PromotionItemController promotionItemController,
                           PromotionController promotionController) {
        this.promotionItemController = promotionItemController;
        this.itemController = itemController;
        this.promotionController = promotionController;
    }

    // 프로그램 실행 메서드
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

    // 초기화 관련 메서드
    private Store initializeStore() throws IOException {
        List<Promotion> promotions = getPromotions();
        List<Item> items = getItems(promotions);
        return new Store(items);
    }

    // 구매 프로세스 처리 메서드
    private String processPurchaseInStore(Store store, Scanner scanner) {
        printStoreInfo(store);

        Map<String, Stock> itemAndStock = getStringStockMap(scanner, store);
        String membershipInput = getMembership(scanner);

        List<Item> purchasedItems = storeController.buyProcess(itemAndStock);

        Receipt receipt = new Receipt(purchasedItems, false);
        this.receiptController = new ReceiptController(receipt);

        receiptMagic(store, scanner, receipt, membershipInput);
        return isContinue(scanner);
    }

    // 반복 확인 메서드
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

    // 영수증 관련 메서드
    private void receiptMagic(Store store, Scanner scanner, Receipt receipt, String membershipInput) {
        receiptController.notifyStockForFree(store, scanner);
        receiptController.notifyPurchasedInfo(store, scanner);
        receipt.calculatePrice();

        if (isUserContinuing(membershipInput)) {
            receiptController.checkMembership();
        }
        OutputView.printReceipt(receipt);
    }

    // 프로모션 정보 초기화 메서드
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

    // 재고 및 구매 항목 관련 메서드
    private Map<String, Stock> getStringStockMap(Scanner scanner, Store store) {
        try {
            Map<String, Stock> itemAndStock = getStringStockMap(scanner);
            invalidStockValidator(itemAndStock, store);
            return itemAndStock;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getStringStockMap(scanner, store);
        }
    }

    private Map<String, Stock> getStringStockMap(Scanner scanner) {
        String itemInput = InputView.getBuyProductMessage(scanner);
        Validator.buyInputFormatValidator(itemInput);
        return getStringStockMap(itemInput);
    }

    private void invalidStockValidator(Map<String, Stock> itemAndStock, Store store) {
        itemAndStock.forEach((name, stock) -> {
            Item item = store.findProduct(name);
            Item promotionItem = store.findPromotionProduct(name);
            storeController.isValidStock(stock, item, promotionItem);
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

    // 멤버십 확인 메서드
    private String getMembership(Scanner scanner) {
        try {
            return isUseMembership(scanner);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getMembership(scanner);
        }
    }

    private String isUseMembership(Scanner scanner) {
        String membershipInput = InputView.getMembershipMessage(scanner);
        Validator.YesOrNoValidator(membershipInput);
        return membershipInput;
    }

    // 스토어 정보 출력 메서드
    private void printStoreInfo(Store store) {
        OutputView.printGreeting();
        OutputView.printStoreInformation(store);
    }

}
