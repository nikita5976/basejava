package webapp.storage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ObjectStreamPathStorage extends AbstractPathStorage{

    protected ObjectStreamPathStorage (String directory){
        super(directory);
    }

    @Override
    protected void doWrite(ByteArrayOutputStream baos, Path path) throws IOException {
        Files.write(path, baos.toByteArray());
    }

    @Override
    protected InputStream  doRead(Path path) throws IOException {
        return new ByteArrayInputStream( Files.readAllBytes( path));
    }
}
