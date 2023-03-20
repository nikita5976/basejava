package webapp.storage;

import webapp.storage.strategy.ObjectStreamSerializer;

import java.io.File;

public class PathStorageTest extends AbstractStorageTest{
    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public PathStorageTest() {
        super(new PathStorage (STORAGE_DIR, new ObjectStreamSerializer()));
    }
}
