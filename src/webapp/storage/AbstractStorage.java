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
        Object key = getExistingSearchKey(uuid);
        return doGet(key);
    }

    @Override
    public final void delete(String uuid) {
        Object key = getExistingSearchKey(uuid);
        doDelete(key);
    }

    @Override
    public final void update(Resume r) {
        Object key = getExistingSearchKey(r.getUuid());
        doUpdate(key, r);
    }


    private void getNotExistingSearchKey(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    abstract protected void doSave(Resume r);

    abstract protected Resume doGet(Object key);

    abstract protected void doDelete(Object key);

    abstract protected void doUpdate(Object key, Resume r);

    abstract protected boolean isExist(Object key);

    abstract protected Object getSearchKey(String uuid);

}