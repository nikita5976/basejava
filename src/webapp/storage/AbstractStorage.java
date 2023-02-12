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
        Object searchKey = getNotExistingSearchKey(r);
        doSave(r, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> allSorted = getAll();
        allSorted.sort(GET_ALL_COMPARATOR);
        return allSorted;
    }

    @Override
    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
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

    abstract protected void doSave(Resume r, Object searchKey);

    abstract protected Resume doGet(Object searchKey);

    abstract protected void doDelete(Object searchKey);

    abstract protected void doUpdate(Object searchKey, Resume r);

    abstract protected boolean isExist(Object searchKey);

    abstract protected Object getSearchKey(String uuid);

    abstract protected List<Resume> getAll();

}