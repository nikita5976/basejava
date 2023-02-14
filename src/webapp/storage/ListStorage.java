package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage <Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume r, Integer key) {
        storage.add(r);
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    public void doDelete(Integer searchKey) {
        storage.remove(searchKey.intValue());
    }

    @Override
    public List<Resume> getAll() {
        return storage;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void doUpdate(Integer searchKey, Resume r) {
        storage.set(searchKey, r);
        System.out.println("\n резюме " + r.getUuid() + " обновлено");
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected final Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
