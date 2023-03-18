package webapp.storage.strategy;

import webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface MethodsObjectStreamStorage<SK>   {


    void doSave(Resume r, SK searchKey);

    Resume doGet(SK searchKey);

    void doDelete(SK searchKey);

    void doUpdate(Resume r, SK searchKey);

    boolean isExist(SK searchKey);

    SK getSearchKey(String uuid);

    List<Resume> doCopyAll();

    void clear();

    int size();

     void doWrite(Resume r, OutputStream os) throws IOException;

     Resume doRead(InputStream is) throws IOException;

}
