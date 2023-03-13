package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage <File> implements StrategyStorage<File> {
    private final File directory;

    protected AbstractFileStorage(String directory) {
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
        File[] list = directory.listFiles();
        if (list != null) {
            for (int i = list.length; --i >= 0; ) {
                doDelete(list[i]);
            }
        } else {
            throw new StorageException("directory error", directory.getAbsolutePath());
        }
    }

    @Override
    public int size() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new StorageException("directory error", directory.getAbsolutePath());
        }
        return list.length;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public void doUpdate(Resume r, File file) {
        try {
            doWrite(r, new BufferedOutputStream ( new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
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
            doUpdate(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error" + file.getAbsolutePath(), file.getName(), e);
        }
    }

    @Override
    public Resume doGet(File file) {

        try {
            return doRead(new BufferedInputStream (new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
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
        File[] list = directory.listFiles();
        List<Resume> resumeList = new ArrayList<>();
        if (list != null) {
            for (int i = list.length; --i >= 0; ) {
                resumeList.add(doGet(list[i]));
            }
        } else {
            throw new StorageException("directory error", directory.getAbsolutePath());
        }
        return resumeList;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;
}

