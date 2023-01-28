package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public final void save(Resume r) {
        checkExist(r);
        doSave(r);
    }

    @Override
    public final Resume get(String uuid) {
        checkNotExist(uuid);
        return extractResume(uuid);
    }

    @Override
    public final void delete(String uuid) {
        checkNotExist(uuid);
        eraseResume(uuid);
    }

    @Override
    public final void update(Resume r) {
        checkNotExist(r.getUuid());
        updateResume(r);
    }

    abstract protected void doSave(Resume r);

    abstract protected Resume extractResume(String uuid);

    abstract protected void eraseResume(String uuid);

    abstract protected void updateResume(Resume r);

    private void checkExist(Resume r) {
        if (isExist(r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    private void checkNotExist(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
    }

    abstract protected boolean isExist(String uuid);

}