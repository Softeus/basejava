package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import static java.lang.Math.abs;

public class SortedArrayStorage extends AbstractArrayStorage{
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
        } else {
            storage[index] = r;
        }
    }

    @Override
    public void save(Resume r) {
        int index = getIndex(r.getUuid());

        if (abs(index) > size || size == 0) {
            storage[size] = r;
            size++;
        } else {
            System.arraycopy(storage, abs(index)-1, storage, abs(index), size);
            storage[abs(index)-1] = r;
            size++;
        }
        if (size >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        }
        if (index >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}