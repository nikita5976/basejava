package webapp.storage;

import webapp.exception.StorageException;
import webapp.model.Resume;

import java.util.*;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Resume r, Object key) {
        int index = (int) key;
        if (size >= storage.length) {
            throw new StorageException("\n резюме " + r.getUuid() + " не сохранено, архив полон", r.getUuid());
        } else {
            saveResume(size, index, r);
            size++;
        }
    }

    public final Resume doGet(Object key) {
        int keyInt = (Integer) key;
        return storage[keyInt];
    }

    public final void doDelete(Object key) {
        int keyInt = (Integer) key;
        deleteResume(size, keyInt);
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
    public final void doUpdate(Object key, Resume r) {
        int keyInt = (Integer) key;
        storage[keyInt] = r;
        System.out.println("\n резюме " + r.getUuid() + " обновлено");
    }

    @Override
    protected boolean isExist(Object key) {
        int keyInt = (Integer) key;
        return keyInt >= 0;
    }

    @Override
    protected final Integer getSearchKey(String uuid) {
        return getIndex(uuid);
    }

    protected abstract void saveResume(int size, int index, Resume r);

    protected abstract void deleteResume(int size, int index);

    protected abstract int getIndex(String uuid);

}
