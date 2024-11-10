package store.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class InputView {
    private static final String BUY_PRODUCT_MESSAGE = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    private static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String ENDING_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    public static String getLines(BufferedReader br) throws IOException {
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
}
