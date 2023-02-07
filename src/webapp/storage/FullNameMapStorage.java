package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class FullNameMapStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    private static String uuid = "empty";

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
        final boolean [] is = {false};
        String keyMap = (String) key;
        storage.forEach((k, r) ->  {
            if (Objects.equals(uuid, r.getUuid())) is [0] = true;
        });
        if (storage.containsKey(keyMap)) is[0] = true;
        return is[0];
    }

    @Override
    protected Object getSearchKey(Resume r) {
        uuid = r.getUuid();
        return r.getFullName();
    }

    @Override
    protected Object getSearchKey(String fullName) {
        uuid = "empty";
        String[] name = {fullName};
        storage.forEach((k, r) ->  {
            if (Objects.equals(fullName, r.getUuid())) name [0] = r.getFullName();
        });
        return name[0];
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
