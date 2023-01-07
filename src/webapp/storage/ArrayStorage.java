package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private final static int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.getUuid() + " не сохранено, архив полон");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("\n Резюме " + r.getUuid() + " уже существует");
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("\n резюме " + uuid + " отсутствует");
            return null;
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("\n резюме " + uuid + " не было в архиве");
        } else {
            storage[index] = storage[size - 1];
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("\n резюме " + r.getUuid() + " не было в архиве");
        } else {
            storage[index] = r;
            System.out.println("\n резюме " + r.getUuid() + " обновлено");
        }
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
