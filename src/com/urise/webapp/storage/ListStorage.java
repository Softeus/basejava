package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ArrayList based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> storage = new ArrayList<>();
    protected int size = 0;

    public int size() {
        return size;
    }

    public void clear() {
        storage.clear();
        size = 0;
    }

    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        }
        storage.set(index, r);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Object[] resumes = storage.toArray(new Resume[0]);
        return (Resume[]) resumes;
    }

    public void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        }
        storage.add(r);
        size++;
        Collections.sort(storage);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        fillDeletedElement(index);
        size--;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }

    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }

    @Override
    protected void insertElement(Resume r, int index) {

    }

    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }
}