package webapp.storage;

import java.io.File;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR));
    }
}
