package resumebase;

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

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if (size >= storage.length) {
            System.out.println("\n резюме " + r.uuid + " не сохранено, архив полон");
            return;
        }
        if (getNumber(r.uuid) == storage.length) {
            storage[size++] = r;
        } else {
            System.out.println("\n Резюме " + r.uuid + " уже существует");
        }
    }

    Resume get(String uuid) {
        int number = getNumber(uuid);
        if (number != storage.length) {
            return storage[number];
        }
        System.out.println("\n резюме " + uuid + " отсутствует");
        return null;
    }

    void delete(String uuid) {
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
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }

    void update(Resume r) {
        int number = getNumber(r.uuid);
        if (number != storage.length) {
            storage[number] = r;
            System.out.println("\n резюме " + r.uuid + " обновлено");
        } else {
            System.out.println("\n резюме " + r.uuid + " не было в архиве");
        }
    }

    // метод getNumber возвращает номер позиции в массиве резюме по его uuid и длину массива если такого uuid нет
    int getNumber(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return i;
            }
        }
        return storage.length;
    }
}
