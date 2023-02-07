package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FullNameMapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume r, Object key) {
        storage.put(r.getFullName(), r);
    }

    @Override
    protected Resume doGet(Object key) {
        String keyMap = (String) key;
        return storage.get(keyMap);
    }

    @Override
    protected void doDelete(Object key) {
        String keyMap = (String) key;
        storage.remove(keyMap);
    }

    @Override
    protected void doUpdate(Object key, Resume r) {
        String keyMap = (String) key;
        storage.put(keyMap, r);
    }

    @Override
    protected boolean isExist(Object key) {
        String keyMap = (String) key;
        return storage.containsKey(keyMap);
    }

    @Override
    protected Object getSearchKey(Resume r) {
        return r.getFullName();
    }

    @Override
    protected Object getSearchKey(String fullName) {
        return fullName;
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
