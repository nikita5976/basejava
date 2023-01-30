package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    public Resume doGet(Object key) {
        int keyInt = (Integer) key;
        return storage.get(keyInt);
    }

    @Override
    public void doDelete(Object key) {
        int keyInt = (Integer) key;
        storage.remove(keyInt);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[storage.size()];
        storage.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void doUpdate(Object key, Resume r) {
        int keyInt = (Integer) key;
        storage.set(keyInt, r);
        System.out.println("\n резюме " + r.getUuid() + " обновлено");
    }

    @Override
    protected boolean isExist(Object key) {
        int keyInt = (Integer) key;
        return keyInt >= 0;
    }

    @Override
    protected final Object getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
