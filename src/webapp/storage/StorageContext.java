package webapp.storage;

import webapp.model.Resume;

import java.util.List;

public class StorageContext<SK> extends AbstractStorage<SK> {
    private StrategyStorage<SK> strategy;

    protected void setStrategy(StrategyStorage<SK> strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void doSave(Resume r, SK searchKey) {
        strategy.doSave(r, searchKey);
    }

    @Override
    protected Resume doGet(SK searchKey) {
        return strategy.doGet(searchKey);
    }

    @Override
    protected void doDelete(SK searchKey) {
        strategy.doDelete(searchKey);
    }

    @Override
    protected void doUpdate(Resume r, SK searchKey) {
        strategy.doUpdate(r, searchKey);
    }

    @Override
    protected boolean isExist(SK searchKey) {
        return strategy.isExist(searchKey);
    }

    @Override
    protected SK getSearchKey(String uuid) {
        return   strategy.getSearchKey(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return strategy.doCopyAll();
    }

    @Override
    public void clear() {
        strategy.clear();
    }

    @Override
    public int size() {
        return strategy.size();
    }
}
