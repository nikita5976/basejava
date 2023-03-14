package webapp.storage;

import java.io.File;
import java.nio.file.Path;

public class SwitchStorage {
    StorageContext storageContext = new StorageContext<>();

    SwitchStorage(ChoiceStorage choice, String StorageDir) {
        switch (choice) {
            case FILE:
                this.storageContext.setStrategy(new <File>ObjectStreamStorage(StorageDir));
                break;
            case PATCH:
                this.storageContext.setStrategy(new <Path>ObjectStreamPathStorage(StorageDir));
        }
    }
}
