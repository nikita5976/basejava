package webapp.storage;


import webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume r) {
        storage.put(r.getUuid(), r);
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
    protected Object getSearchKey(String uuid) {
        return  uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        ArrayList<Resume> resumesList = new ArrayList<>(storage.values());
        Resume[] resumes = new Resume[storage.size()];
        resumesList.toArray(resumes);
        return resumes;
    }

    @Override
    public int size() {
        return storage.size();
    }
}


