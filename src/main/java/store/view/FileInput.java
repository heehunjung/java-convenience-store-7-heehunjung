package store.view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileInput {

    private FileInput() {

    }

    public static final String ITEM_FILE_NAME = "products.md";
    public static final String PROMOTION_FILE_NAME = "promotions.md";

    public static BufferedReader FileInputSetting(String fileName) {
        InputStream inputStream = FileInput.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalStateException("File not found: products.md");
        }

        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
