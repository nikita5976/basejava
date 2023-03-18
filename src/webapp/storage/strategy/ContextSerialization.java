package webapp.storage.strategy;

import webapp.model.Resume;
import webapp.storage.AbstractStorage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ContextSerialization<SK> extends AbstractStorage<SK> implements MethodsSerialization<SK> {
    private MethodsSerialization<SK> context;

    public ContextSerialization(){}

    public ContextSerialization ( ChoiceStorage choiceStorage, String directory){
        ChoiceSerialization choiceSerialization = new ChoiceSerialization(choiceStorage, directory );
        this.context = choiceSerialization.context;
    }

    public void setContext(MethodsSerialization<SK> context) {
        this.context = context;
    }


    @Override
    public void doSave(Resume r, SK searchKey) {
        context.doSave(r, searchKey);
    }

    @Override
    public Resume doGet(SK searchKey) {
        return context.doGet(searchKey);
    }

    @Override
    public void doDelete(SK searchKey) {
        context.doDelete(searchKey);
    }

    @Override
    public void doUpdate(Resume r, SK searchKey) {
        context.doUpdate(r, searchKey);
    }

    @Override
    public boolean isExist(SK searchKey) {
        return context.isExist(searchKey);
    }

    @Override
    public SK getSearchKey(String uuid) {
        return context.getSearchKey(uuid);
    }

    @Override
    public List<Resume> doCopyAll() {
        return context.doCopyAll();
    }

    @Override
    public void clear() {
        context.clear();
    }

    @Override
    public int size() {
        return context.size();
    }

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        context.doWrite(r, os);
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        return context.doRead(is);
    }


}
