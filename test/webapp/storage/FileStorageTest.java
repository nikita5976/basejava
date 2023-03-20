package webapp.storage;

import webapp.storage.strategy.ObjectStreamSerializer;

import java.io.File;

public class FileStorageTest extends AbstractStorageTest{

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR,new ObjectStreamSerializer()));
    }
}
