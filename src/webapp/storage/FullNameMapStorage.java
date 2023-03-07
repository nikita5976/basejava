package webapp.storage;

import webapp.model.Resume;

import java.util.*;

public class FullNameMapStorage extends AbstractStorage <String>{
    private final Map<String, Resume> storage = new HashMap<>();

    private static String uuid = "empty";

    @Override
    protected void doSave(Resume r, String searchKey) {
        storage.put(r.getFullName(), r);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        storage.put(searchKey, r);
    }

    @Override
    protected boolean isExist(String searchKey) {
        final boolean [] is = {false};
        storage.forEach((k, r) ->  {
            if (Objects.equals(uuid, r.getUuid())) is [0] = true;
        });
        if (storage.containsKey(searchKey)) is[0] = true;
        return is[0];
    }

    @Override
    protected String getSearchKey(Resume r) {
        uuid = r.getUuid();
        return r.getFullName();
    }

    @Override
    protected String getSearchKey(String fullName) {
        uuid = "empty";
        String[] name = {fullName};
        storage.forEach((k, r) ->  {
            if (Objects.equals(fullName, r.getUuid())) name [0] = r.getFullName();
        });
        return name[0];
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
