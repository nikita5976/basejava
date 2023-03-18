package webapp.storage;

import webapp.storage.strategy.WaysStorage;
import webapp.storage.strategy.ContextObjectStreamStorage;

import java.io.File;

public class ChoiceSerializationTest extends AbstractStorageTest {
    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public ChoiceSerializationTest() {
        super(new ContextObjectStreamStorage<>(WaysStorage.FILE, STORAGE_DIR));
    }
}
