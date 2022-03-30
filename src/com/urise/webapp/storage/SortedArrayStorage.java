package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.lang.Math.abs;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void writeResume(Resume r) {
        int index = getIndex(r.getUuid());
        System.arraycopy(storage, abs(index) - 1, storage, abs(index), size);
        storage[abs(index) - 1] = r;
        size++;
    }

    @Override
    protected void deleteResume(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, abs(index) + 1, storage, abs(index), size - abs(index));
        size--;
    }
}