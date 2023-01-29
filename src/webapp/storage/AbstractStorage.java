package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

public abstract class AbstractStorage implements Storage {


    @Override
    public final void save(Resume r) {
        getNotExistingSearchKey(r);
        doSave(r);
    }

    @Override
    public final Resume get(String uuid) {
        int key = getExistingSearchKey(uuid);
        return doGet(key);
    }

    @Override
    public final void delete(String uuid) {
        int key = getExistingSearchKey(uuid);
        doDelete(key);
    }

    @Override
    public final void update(Resume r) {
        int key = getExistingSearchKey(r.getUuid());
        doUpdate(key,r);
    }

    abstract protected void doSave(Resume r);

    abstract protected Resume doGet(int key);

    abstract protected void doDelete(int key);

    abstract protected void doUpdate(int key,Resume r);

    private void getNotExistingSearchKey(Resume r) {
        int key = getKey (r.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    private int getExistingSearchKey(String uuid) {
        int key = getKey (uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    abstract protected boolean isExist(int key);
    abstract protected int getKey (String uuid);

}