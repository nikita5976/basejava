package webapp.storage;

import java.io.File;


public class FileStorageTest extends AbstractStorageTest {
    private final static File DIRECTORY = new File("C:\\test\\resume");
    static {
        if (!DIRECTORY.exists()) {
            DIRECTORY.mkdirs();
        }
    }

    public FileStorageTest() {
        super(new FileStorage(DIRECTORY));
    }
}
