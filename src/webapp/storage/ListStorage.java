package webapp.storage;

import webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    private final ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    public Resume extractResume(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) {
                return r;
            }
        }
        return null;
    }

    @Override
    public void eraseResume(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) {
                storage.remove(r);
                return;
            }
        }
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
    public void updateResume(Resume r) {
        int index = storage.indexOf(r);
        storage.set(index, r);
        System.out.println("\n резюме " + r.getUuid() + " обновлено");
    }

    @Override
    protected boolean isExist(String uuid) {
        for (Resume r : storage) {
            if (uuid.equals(r.getUuid())) {
                return true;
            }
        }
        return false;
    }
}
