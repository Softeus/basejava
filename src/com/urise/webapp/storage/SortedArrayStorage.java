package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import static java.lang.Math.abs;

public class SortedArrayStorage extends AbstractArrayStorage{

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

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}