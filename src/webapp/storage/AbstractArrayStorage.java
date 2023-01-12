package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("\n резюме " + uuid + " отсутствует");
            return null;
        }
        return storage[index];
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("\n резюме " + r.getUuid() + " не было в архиве");
        } else {
            storage[index] = r;
            System.out.println("\n резюме " + r.getUuid() + " обновлено");
        }
    }

    protected abstract int getIndex(String uuid);
}
