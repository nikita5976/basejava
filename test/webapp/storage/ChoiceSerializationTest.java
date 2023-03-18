package webapp.storage;

import webapp.storage.strategy.ChoiceStorage;
import webapp.storage.strategy.ContextSerialization;

import java.io.File;

public class ChoiceSerializationTest extends AbstractStorageTest {
    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public ChoiceSerializationTest() {
        super(new ContextSerialization<>(ChoiceStorage.FILE, STORAGE_DIR));
    }
}
