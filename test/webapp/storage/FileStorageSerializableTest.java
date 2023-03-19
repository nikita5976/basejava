package webapp.storage;

import webapp.storage.strategy.FileStorage;
import webapp.storage.strategy.ObjectStreamSerializer;

import java.io.File;

public class FileStorageSerializableTest extends AbstractStorageTest{

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public FileStorageSerializableTest() {
        super(new FileStorage(new ObjectStreamSerializer(), STORAGE_DIR));
    }
}
