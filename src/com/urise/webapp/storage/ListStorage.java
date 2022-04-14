package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */
public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
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
        Collections.sort(storage);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        fillDeletedElement(index);
    }

    @Override
    protected Resume getElementByIndex(int index) {
        return storage.get(index);
    }

    protected void fillDeletedElement(int index) {
        storage.remove(index);
    }

    protected int getIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    protected void updateElementAtIndex(Resume r, int index) {
        storage.set(index, r);
    }
}