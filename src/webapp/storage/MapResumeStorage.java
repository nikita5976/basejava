package webapp.storage;


import webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

public class MapResumeStorage extends AbstractStorage <Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doSave(Resume r, Resume searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    protected void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    protected void doUpdate(Resume r, Resume searchKey) {
        storage.put(searchKey.getUuid(), r);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return !isNull(searchKey);
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
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
