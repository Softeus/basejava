package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.TreeMap;

public class MapStorage extends AbstractStorage {
    protected TreeMap<String, Resume> storage = new TreeMap<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }

    public Resume[] getAll() {
        Object[] resumes = storage.values().toArray(new Resume[0]);
        return (Resume[]) resumes;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        storage.remove(uuid);
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(uuid);
    }

    protected Resume getResume(int index) {
        return null;
    }

    protected void deleteResume(int index) {
    }

    protected void insertResume(Resume r, int index) {
        storage.put(r.getUuid(), r);
    }

    protected int getIndex(String uuid) {
        return storage.containsKey(uuid) ? 1 : -1;
    }

    protected void updateResume(Resume r, int index) {
        storage.replace(r.getUuid(), r);
    }
}

