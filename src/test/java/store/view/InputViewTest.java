package store.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputViewTest {

    @DisplayName("getLines_테스트_01")
    @Test
    void getLines_기능_테스트() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.ITEM_FILE_NAME);
        List<String> expected = Arrays.asList(
                "콜라,1000,10,탄산2+1",
                "콜라,1000,10,null",
                "사이다,1000,8,탄산2+1",
                "사이다,1000,7,null",
                "오렌지주스,1800,9,MD추천상품",
                "탄산수,1200,5,탄산2+1",
                "물,500,10,null",
                "비타민워터,1500,6,null",
                "감자칩,1500,5,반짝할인",
                "감자칩,1500,5,null",
                "초코바,1200,5,MD추천상품",
                "초코바,1200,5,null",
                "에너지바,2000,5,null",
                "정식도시락,6400,8,null",
                "컵라면,1700,1,MD추천상품",
                "컵라면,1700,10,null"
        );

        Assertions.assertThat(InputView.getLines(br))
                .containsAll(expected);
    }

    @DisplayName("getLine_테스트_01")
    @Test
    void getLines_기능_테스트_PRODUCT_파일() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.ITEM_FILE_NAME);
        InputView.getLine(br);
        String firstLine = InputView.getLine(br);

        Assertions.assertThat(firstLine)
                .isEqualTo("콜라,1000,10,탄산2+1");
    }

    @DisplayName("getLine_테스트_02")
    @Test
    void getLines_기능_테스트_PROMOTION_파일() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.PROMOTION_FILE_NAME);
        InputView.getLine(br);
        String firstLine = InputView.getLine(br);

        Assertions.assertThat(firstLine)
                .isEqualTo("탄산2+1,2,1,2024-01-01,2024-12-31");
    }
}
