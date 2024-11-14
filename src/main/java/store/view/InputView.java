package store.view;

import static store.global.InputConstant.BUY_PRODUCT_MESSAGE;
import static store.global.InputConstant.ENDING_MESSAGE;
import static store.global.InputConstant.MEMBERSHIP_MESSAGE;
import static store.global.InputConstant.NOTICE_FOR_FREE_STOCK;
import static store.global.InputConstant.NOTICE_FOR_FREE_STOCK_MESSAGE;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private InputView() {

    }

    public static List<String> getLines(BufferedReader br) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = getLine(br)) != null) {
            lines.add(line);
        }
        return lines;
    }

    public static String getLine(BufferedReader br) throws IOException {
        return br.readLine();
    }

    public static String getBuyProductMessage(Scanner sc) {
        System.out.println(BUY_PRODUCT_MESSAGE);
        return sc.nextLine();
    }

    public static String getMembershipMessage(Scanner sc) {
        System.out.println(MEMBERSHIP_MESSAGE);
        return sc.nextLine();
    }

    public static String getEndingMessage(Scanner sc) {
        System.out.println(ENDING_MESSAGE);
        return sc.nextLine();
    }

    public static String getMoreFreeStock(Scanner sc, String name, int freeStock) {
        System.out.printf(NOTICE_FOR_FREE_STOCK_MESSAGE, name, freeStock);
        return sc.nextLine();
    }

    public static String getAgreeBuyWithNoPromotion(Scanner sc, String name, int stock) {
        System.out.printf(NOTICE_FOR_FREE_STOCK, name, stock);
        return sc.nextLine();
    }
}
