package webapp.storage;

import java.io.File;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
