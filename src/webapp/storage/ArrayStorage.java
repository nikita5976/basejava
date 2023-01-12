package webapp.storage;

import webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.getUuid() + " не сохранено, архив полон");
        } else if (getIndex(r.getUuid()) != -1) {
            System.out.println("\n Резюме " + r.getUuid() + " уже существует");
        } else {
            storage[size++] = r;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("\n резюме " + uuid + " не было в архиве");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
