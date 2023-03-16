package webapp.storage;

import webapp.storage.strategy.ChoiceSerialization;
import webapp.storage.strategy.ChoiceStorage;

import java.io.File;

public class ChoiceSerializationTest extends AbstractStorageTest {
    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public ChoiceSerializationTest() {
        super(new ChoiceSerialization(ChoiceStorage.PATCH, STORAGE_DIR).context);
    }
}
