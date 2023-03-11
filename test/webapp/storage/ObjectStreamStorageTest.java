package webapp.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {

    static {
        if (!STORAGE_DIR.exists()) {
            STORAGE_DIR.mkdirs();
        }
    }

    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
