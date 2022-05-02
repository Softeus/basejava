package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapResumeStorage extends AbstractStorage {
    protected Map<String, Resume> storage = new TreeMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.getOrDefault(uuid, null);
    }

    @Override
    protected void doUpdate(Resume r, Object searchKey) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Resume r, Object searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public List<Resume> doCopyAll() {
        return Collections.emptyList();
    }
}