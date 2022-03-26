package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int numberOfResumes = 0;

    public void clear() {
        Arrays.fill(storage, 0, numberOfResumes - 1, null);
        numberOfResumes = 0;
    }

    public void save(Resume r) {
        for (int i = 0; i < numberOfResumes; i++)
            if (Objects.equals(storage[i].getUuid(), r.getUuid())) {
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
        for (int i = 0; i < numberOfResumes; i++)
            if (Objects.equals(storage[i].getUuid(), r.getUuid())) {
                storage[i] = r;
                return;
            }
        System.out.println("Резюме с UUID: " + r.getUuid() + " не найдено");
    }

    public Resume get(String uuid) {
        for (int i = 0; i < numberOfResumes; i++)
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                return storage[i];
            }
        System.out.println("Резюме с UUID: " + uuid + " не найдено");
        return null;
    }

    public void delete(String uuid) {
        for (int i = 0; i < numberOfResumes; i++)
            if (Objects.equals(storage[i].getUuid(), uuid)) {
                numberOfResumes--;
                System.arraycopy(storage, i + 1, storage, i, numberOfResumes - i);
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
}