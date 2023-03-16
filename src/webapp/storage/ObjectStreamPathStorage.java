package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage{

    public ObjectStreamPathStorage (String directory){
        super(directory);
    }

    @Override
    public void doWrite(Resume resume, Path path) throws IOException {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        ObjectOutputStream ois = new ObjectOutputStream(boas);
            ois.writeObject(resume);
            Files.write(path, boas.toByteArray());
    }

    @Override
    public Resume doRead(InputStream bais) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException(" Resume Class is not", null, e);
        }
    }
}
