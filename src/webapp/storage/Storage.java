package webapp.storage;

import webapp.model.Resume;

import java.util.List;

public interface Storage {
    void clear();

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();

    void update(Resume r);
}
