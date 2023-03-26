package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;
import webapp.storage.strategy.StreamSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamSerializer serializer;

    protected FileStorage(String directory, StreamSerializer serializer) {
        this.serializer = serializer;
        File tempDirectory = new File(directory);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!tempDirectory.isDirectory()) {
            throw new IllegalArgumentException(tempDirectory.getAbsolutePath() + " is not directory");
        }
        if (!tempDirectory.canRead() || !tempDirectory.canWrite()) {
            throw new IllegalArgumentException(tempDirectory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = tempDirectory;
    }

    @Override
    public void clear() {
            for (File f: getListFile()) {
                doDelete(f);
            }
    }

    @Override
    public int size() {
        return getListFile().length;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void doUpdate(Resume r, File file) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("write to file error", file.getName(), e);
        }
    }

    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void doSave(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("create new file error" + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    public Resume doGet(File file) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("read file error", file.getName(), e);
        }
    }

    @Override
    public void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("directory error", file.getAbsolutePath());
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> resumeList = new ArrayList<>();
            for (File f : getListFile()) {
                resumeList.add(doGet(f));
            }
        return resumeList;
    }

    private File[] getListFile () {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("directory error", directory.getAbsolutePath());
        }
        return list;
    }

    private void doWrite(Resume resume, OutputStream os) throws IOException {
        serializer.doWrite(resume, os);
    }

    private Resume doRead(InputStream is) throws IOException {
        return serializer.doRead(is);
    }
}

