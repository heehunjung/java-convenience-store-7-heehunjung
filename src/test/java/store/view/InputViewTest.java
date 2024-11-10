package store.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputViewTest {

    @DisplayName("getLine_테스트_01")
    @Test
    void getLine_기능_테스트_PRODUCT_파일() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.PRODUCT_FILE_NAME);
        InputView.getLines(br);
        String firstLine = InputView.getLines(br);

        Assertions.assertThat(firstLine)
                .isEqualTo("콜라,1000,10,탄산2+1");
    }

    @DisplayName("getLine_테스트_02")
    @Test
    void getLine_기능_테스트_PROMOTION_파일() throws IOException {
        BufferedReader br = FileInput.FileInputSetting(FileInput.PROMOTION_FILE_NAME);
        InputView.getLines(br);
        String firstLine = InputView.getLines(br);

        Assertions.assertThat(firstLine)
                .isEqualTo("탄산2+1,2,1,2024-01-01,2024-12-31");
    }
}
