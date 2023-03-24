package webapp.storage;

import webapp.storage.strategy.XmlStreamSerializer;

import java.io.File;

public class XmlPathStorageTest extends AbstractStorageTest {

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}