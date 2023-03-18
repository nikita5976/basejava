package webapp.storage.strategy;

import webapp.storage.ObjectStreamPathStorage;
import webapp.storage.ObjectStreamStorage;

import java.io.File;
import java.nio.file.Path;

public class ChoiceSerialization  {
    public ContextObjectStreamStorage context = new ContextObjectStreamStorage<>();

    public ChoiceSerialization(WaysStorage choice, String StorageDir) {
        switch (choice) {
            case FILE:
                this.context.setContext(new <File>ObjectStreamStorage(StorageDir));
                break;
            case PATCH:
                this.context.setContext(new <Path>ObjectStreamPathStorage(StorageDir));
        }
    }
}
