package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    private static final Comparator<Resume> GET_ALL_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);


    @Override
    public final void save(Resume r) {
        Object key = getNotExistingSearchKey(r);
        doSave(r, key);
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
    public final List<Resume> getAllSorted() {
        List<Resume> allSorted = getAll();
        allSorted.sort(GET_ALL_COMPARATOR);
        return allSorted;
    }

    @Override
    public final void update(Resume r) {
        Object key = getExistingSearchKey(r.getUuid());
        doUpdate(key, r);
    }


    private Object getNotExistingSearchKey(Resume r) {
        Object searchKey = getSearchKey(r);
        if (isExist(searchKey)) {
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected Object getSearchKey(Resume r) {
        return getSearchKey(r.getUuid());
    }

    abstract protected void doSave(Resume r, Object key);

    abstract protected Resume doGet(Object key);

    abstract protected void doDelete(Object key);

    abstract protected void doUpdate(Object key, Resume r);

    abstract protected boolean isExist(Object key);

    abstract protected Object getSearchKey(String uuid);

    abstract protected List<Resume> getAll();

}