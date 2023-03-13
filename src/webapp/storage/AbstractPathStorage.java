package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) ||!Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try (Stream<Path> stream = Files.list(directory)) {
            stream.forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try (Stream<Path> stream = Files.list(directory)) {
            return (int) stream.count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(resume);
            doWrite(boas.toByteArray(), path);
        } catch (IOException e) {
            throw new StorageException("Error write", path.toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            doUpdate(r, path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        Resume resume;

        try {
            InputStream is = new ByteArrayInputStream(doRead(path));

            try (ObjectInputStream ois = new ObjectInputStream(is)) {
                resume = (Resume) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new StorageException("IO error", path.toString(), e);
            }
        } catch (IOException e) {
            throw new StorageException("IO error doRead", path.toString(), e);
        }
        return resume;
    }

    @Override
    protected void doDelete(Path path) {
       try {
           Files.delete(path);
       } catch (IOException e){
            throw new StorageException("delete error", path.toString(),e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> resumeList;
        try (Stream <Path> stream = Files.list(directory)) {
             resumeList = stream
                    .map(this::doGet)
                    .collect(Collectors.toList());
        } catch (IOException e) {
           throw new StorageException("Files.list IO error", directory.toString(), e );
        }
        return resumeList;
    }

    protected abstract void doWrite(byte[] bytesResume, Path path) throws IOException;

    protected abstract byte[] doRead(Path path) throws IOException;
}
