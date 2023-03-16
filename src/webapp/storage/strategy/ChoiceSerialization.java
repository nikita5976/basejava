package webapp.storage.strategy;

import webapp.storage.ObjectStreamPathStorage;
import webapp.storage.ObjectStreamStorage;

import java.io.File;
import java.nio.file.Path;

public class ChoiceSerialization  {
    public ContextSerialization context = new ContextSerialization<>();

    public ChoiceSerialization(ChoiceStorage choice, String StorageDir) {
        switch (choice) {
            case FILE:
                this.context.setContext(new <File>ObjectStreamStorage(StorageDir));
                break;
            case PATCH:
                this.context.setContext(new <Path>ObjectStreamPathStorage(StorageDir));
        }
    }
}
