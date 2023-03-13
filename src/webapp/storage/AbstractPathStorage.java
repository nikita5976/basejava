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

public abstract class AbstractPathStorage extends AbstractStorage<Path> implements StrategyStorage <Path>{
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (Files.isDirectory(directory) ||Files.isWritable(directory))  {
        } else {
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
    public Path getSearchKey(String uuid) {
        return Paths.get(directory.toString(), uuid);
    }

    @Override
    public void doUpdate(Resume resume, Path path) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
            ois.writeObject(resume);
            doWrite(boas, path);
        } catch (IOException e) {
            throw new StorageException("Error write", path.toString(), e);
        }
    }

    @Override
    public boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void doSave(Resume r, Path path) {
        try {
            Files.createFile(path);
            doUpdate(r, path);
        } catch (IOException e) {
            throw new StorageException("IO error", path.toString(), e);
        }
    }

    @Override
    public Resume doGet(Path path) {
        Resume resume;
            try (ObjectInputStream ois = new ObjectInputStream(doRead(path))) {
                resume = (Resume) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new StorageException("IO error", path.toString(), e);
            }
        return resume;
    }

    @Override
    public void doDelete(Path path) {
       try {
           Files.delete(path);
       } catch (IOException e){
            throw new StorageException("delete error", path.toString(),e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
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

    protected abstract void doWrite(ByteArrayOutputStream baos, Path path) throws IOException;

    protected abstract InputStream doRead(Path path) throws IOException;
}
