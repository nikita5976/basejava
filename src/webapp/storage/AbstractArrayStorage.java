package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public final void save(Resume r) {
        if (size == 0) {
            storage[0] = r;
            size++;
            return;
        }
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.getUuid() + " не сохранено, архив полон");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("\n Резюме " + r.getUuid() + " уже существует");
            return;
        }
        saveResume(size, index, r);
        size++;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("\n резюме " + uuid + " отсутствует");
            return null;
        }
        return storage[index];
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("\n резюме " + uuid + " не было в архиве");
        } else {
            deleteResume(size, index, uuid);
            size--;
        }
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public final int size() {
        return size;
    }


    public final void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("\n резюме " + r.getUuid() + " не было в архиве");
        } else {
            storage[index] = r;
            System.out.println("\n резюме " + r.getUuid() + " обновлено");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(int size, int index, Resume r);

    protected abstract void deleteResume(int size, int index, String uuid);
}
