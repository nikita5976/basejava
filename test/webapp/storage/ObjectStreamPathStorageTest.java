package webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
   private static final String DIR = STORAGE_DIR.getAbsolutePath();

    static {
        if (!STORAGE_DIR.exists()) {
            STORAGE_DIR.mkdirs();
        }
    }
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(DIR));
    }
}
