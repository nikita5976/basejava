package webapp.storage;

import webapp.storage.strategy.JsonStreamSerializer;

import java.io.File;

public class JsonPathStorageTest extends AbstractStorageTest {

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
