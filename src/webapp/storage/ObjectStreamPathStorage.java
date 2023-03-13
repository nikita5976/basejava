package webapp.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ObjectStreamPathStorage extends AbstractPathStorage{

    protected ObjectStreamPathStorage (String directory){
        super(directory);
    }

    @Override
    protected void doWrite(byte[] bytesResume, Path path) throws IOException {
        Files.write(path, bytesResume);
    }

    @Override
    protected byte[] doRead(Path path) throws IOException {
        return Files.readAllBytes( path);
    }
}
