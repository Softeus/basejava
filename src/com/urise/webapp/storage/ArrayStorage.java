package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int numberOfResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, numberOfResumes, null);
        numberOfResumes = 0;
    }

    public void save(Resume r) {
        if (getPos(r.getUuid()) != -1) {
            System.out.println("Запись не возможна: резюме UUID " + r.getUuid() + " уже есть в базе");
            return;
        }
        if (numberOfResumes < storage.length) {
            storage[numberOfResumes++] = r;
        } else {
            System.out.println("Запись не возможна: база данных переполнена.");
        }
    }

    public void update(Resume r) {
        int pos = getPos(r.getUuid());
        if (pos != -1) {
            storage[pos] = r;
            return;
        }
        System.out.println("Резюме с UUID: " + r.getUuid() + " не найдено");
    }

    public Resume get(String uuid) {
        int pos = getPos(uuid);
        if (pos != -1) {
            return storage[pos];
        }
        System.out.println("Резюме с UUID: " + uuid + " не найдено");
        return null;
    }

    public void delete(String uuid) {
        int pos = getPos(uuid);
        if (pos != -1) {
            numberOfResumes--;
            System.arraycopy(storage, pos + 1, storage, pos, numberOfResumes - pos);
            storage[numberOfResumes] = null;
            return;
        }
        System.out.println("Резюме с UUID " + uuid + " не найдено");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfResumes);
    }

    public int size() {
        return numberOfResumes;
    }

    int getPos(String uuid) {
        for (int i = 0; i < numberOfResumes; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}