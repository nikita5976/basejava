package webapp.storage;

import webapp.exception.NotExistStorageException;
import webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected boolean ExistStorage(Resume r) {
        return storage.contains(r);
    }

    @Override
    protected void addResume(Resume r) {
        storage.add(r);
    }

    @Override
    public Resume get(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) {
                storage.remove(r);
                return;
            }
        }
        throw new NotExistStorageException(uuid);
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
    public void update(Resume r) {
        int index = storage.indexOf(r);
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage.set(index, r);
            System.out.println("\n резюме " + r.getUuid() + " обновлено");
        }
    }
}
