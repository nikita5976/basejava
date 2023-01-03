package webapp.storage;

import webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 * методы реализованы без проверки uuid на уникальность
 * при добавлении resumebase.Resume заносится в следующую свободную справа ячейку массива storage
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    // переменная size соответствует количеству заполненных ячеек массива storage
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.getUuid() + " не сохранено, архив полон");
            return;
        }
        if (getNumber(r.getUuid()) == storage.length) {
            storage[size++] = r;
        } else {
            System.out.println("\n Резюме " + r.getUuid() + " уже существует");
        }
    }

    public Resume get(String uuid) {
        int number = getNumber(uuid);
        if (number != storage.length) {
            return storage[number];
        }
        System.out.println("\n резюме " + uuid + " отсутствует");
        return null;
    }

    public void delete(String uuid) {
        int number = getNumber(uuid);
        if (number != storage.length) {
            storage[number] = storage[size - 1];
            size--;
        } else {
            System.out.println("\n резюме " + uuid + " не было в архиве");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume r) {
        int number = getNumber(r.getUuid());
        if (number != storage.length) {
            storage[number] = r;
            System.out.println("\n резюме " + r.getUuid() + " обновлено");
        } else {
            System.out.println("\n резюме " + r.getUuid() + " не было в архиве");
        }
    }

    // метод getNumber возвращает номер позиции в массиве резюме по его uuid и длину массива если такого uuid нет
    int getNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return storage.length;
    }
}
