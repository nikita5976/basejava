package webapp.storage;


import webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        String keyMap = (String) searchKey;
        return storage.get(keyMap);
    }

    @Override
    protected void doDelete(Object searchKey) {
        String keyMap = (String) searchKey;
        storage.remove(keyMap);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume r) {
        String keyMap = (String) searchKey;
        storage.put(keyMap, r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        String keyMap = (String) searchKey;
        return storage.containsKey(keyMap);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
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


