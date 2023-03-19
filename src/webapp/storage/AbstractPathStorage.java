package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.storage.strategy.FileStorage;
import webapp.storage.strategy.ObjectStreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public  class AbstractPathStorage extends AbstractStorage<Path>  {
    private final Path directory;

    protected AbstractPathStorage(String dir) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (Files.isDirectory(directory) || Files.isWritable(directory)) {
        } else {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        getStreamPath().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getStreamPath().count();
    }

    @Override
    public Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public void doUpdate(Resume resume, Path path) {
        try (OutputStream os = Files.newOutputStream(path)) {
            doWrite(resume, os);
        } catch (IOException e) {
            throw new StorageException("write to file error", path.toString(), e);
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
        } catch (IOException e) {
            throw new StorageException("create new file error", path.toString(), e);
        }
        doUpdate(r, path);
    }

    @Override
    public Resume doGet(Path path) {

        try (InputStream is = Files.newInputStream(path)) {
            return doRead(is);
        } catch (IOException e) {
            throw new StorageException("read file error", path.toString(), e);
        }
    }

    @Override
    public void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("delete error", path.toString(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        return getStreamPath()
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    protected Stream<Path> getStreamPath() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    public void doWrite(Resume resume, OutputStream os) throws IOException {
        new FileStorage(new ObjectStreamSerializer(), "C:\\test\\resume").doWrite(resume, os);
    }

    public Resume doRead(InputStream is) throws IOException {
        return new FileStorage(new ObjectStreamSerializer(),"C:\\test\\resume").doRead(is);
    }
}
