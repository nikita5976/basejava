package webapp.storage.strategy;

import webapp.model.Resume;
import webapp.storage.AbstractFileStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileStorage extends AbstractFileStorage  {

    private final StrategySerializable serializer;

    public FileStorage(StrategySerializable serializer, String directory) {
        super(directory);
        this.serializer = serializer;
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.doWrite(r, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}
