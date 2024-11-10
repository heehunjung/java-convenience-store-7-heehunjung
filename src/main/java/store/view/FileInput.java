package store.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

//TODO: 에러메세지 해결
public class FileInput {
    public static final String PRODUCT_FILE_NAME = "products.md";
    public static final String PROMOTION_FILE_NAME = "promotions.md";

    public static BufferedReader FileInputSetting(String fileName) {

        InputStream inputStream = FileInput.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalStateException("File not found: products.md");
        }

        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
