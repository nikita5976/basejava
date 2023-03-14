package webapp.storage;

import java.io.File;

public class SwitchStorageTest extends AbstractStorageTest{

    static {
        File file = new File(STORAGE_DIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

   public SwitchStorageTest() {
        super (new SwitchStorage(ChoiceStorage.PATCH, STORAGE_DIR).storageContext);
    }
}
