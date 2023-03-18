package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.storage.strategy.MethodsObjectStreamStorage;

import java.io.*;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage implements MethodsObjectStreamStorage<Path> {

    public ObjectStreamPathStorage (String directory){
        super(directory);
    }

    @Override
    public void doWrite (Resume resume, OutputStream os) throws IOException {
        ObjectOutputStream ois = new ObjectOutputStream(os);
            ois.writeObject(resume);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(" Resume Class is not", null, e);
        }
    }
}
