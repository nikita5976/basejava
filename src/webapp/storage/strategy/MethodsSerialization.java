package webapp.storage.strategy;

import webapp.model.Resume;

import java.util.List;

public interface MethodsSerialization <SK>   {


    void doSave(Resume r, SK searchKey);

    Resume doGet(SK searchKey);

    void doDelete(SK searchKey);

    void doUpdate(Resume r, SK searchKey);

    boolean isExist(SK searchKey);

    SK getSearchKey(String uuid);

    List<Resume> doCopyAll();

    void clear();

    int size();

}
