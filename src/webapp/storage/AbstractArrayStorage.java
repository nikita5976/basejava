package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage <Integer> {
    protected final static int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        if (size >= storage.length) {
            throw new StorageException("\n резюме " + r.getUuid() + " не сохранено, архив полон", r.getUuid());
        } else {
            saveResume(size, searchKey, r);
            size++;
        }
    }

    public final Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    public final void doDelete(Integer searchKey) {
        deleteResume(size, searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public final List<Resume> getAll() {
        ArrayList<Resume> all = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            all.add(storage[i]);
        }
        return all;
    }

    @Override
    public final int size() {
        return size;
    }

    @Override
    public final void doUpdate(Integer searchKey, Resume r) {
        storage[searchKey] = r;
        System.out.println("\n резюме " + r.getUuid() + " обновлено");
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected final Integer getSearchKey(String uuid) {
        return getIndex(uuid);
    }

    protected abstract void saveResume(int size, int index, Resume r);

    protected abstract void deleteResume(int size, int index);

    protected abstract int getIndex(String uuid);

}
