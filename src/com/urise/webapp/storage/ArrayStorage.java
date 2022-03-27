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
        if (findIndex(r.getUuid()) != -1) {
            System.out.println("Запись не возможна: резюме UUID " + r.getUuid() + " уже есть в базе");
        } else if (numberOfResumes < storage.length) {
            storage[numberOfResumes++] = r;
        } else {
            System.out.println("Запись не возможна: база данных переполнена.");
        }
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index != -1) {
            storage[index] = r;
        } else {
            System.out.println("Резюме с UUID: " + r.getUuid() + " не найдено");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.println("Резюме с UUID: " + uuid + " не найдено");
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Резюме с UUID " + uuid + " не найдено");
        } else {
            storage[index] = storage[numberOfResumes-1];
            storage[numberOfResumes-1] = null;
            numberOfResumes--;
        }
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

    private int findIndex(String uuid) {
        for (int i = 0; i < numberOfResumes; i++) {
            if (uuid == storage[i].getUuid()) {
                return i;
            }
        }
        return -1;
    }
}