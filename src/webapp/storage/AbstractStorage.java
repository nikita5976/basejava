package webapp.storage;

import webapp.exception.ExistStorageException;
import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    //protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    private static final Comparator<Resume> GET_ALL_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);


    @Override
    public final void save(Resume r) {
        LOG.info("Save " + r);
        SK searchKey = getNotExistingSearchKey(r);
        doSave(r, searchKey);
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> allSorted = getAll();
        allSorted.sort(GET_ALL_COMPARATOR);
        return allSorted;
    }

    @Override
    public final void update(Resume r) {
        LOG.info("Update " + r);
        SK searchKey = getExistingSearchKey(r.getUuid());
        doUpdate(searchKey, r);
    }


    private SK getNotExistingSearchKey(Resume r) {
        SK searchKey = getSearchKey(r);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + r.getUuid() + " not exist");
            throw new ExistStorageException(r.getUuid());
        }
        return searchKey;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected SK getSearchKey(Resume r) {
        return getSearchKey(r.getUuid());
    }

    abstract protected void doSave(Resume r, SK searchKey);

    abstract protected Resume doGet(SK searchKey);

    abstract protected void doDelete(SK searchKey);

    abstract protected void doUpdate(SK searchKey, Resume r);

    abstract protected boolean isExist(SK searchKey);

    abstract protected SK getSearchKey(String uuid);

    abstract protected List<Resume> getAll();

}