package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    Resume resumeUUID1 = new Resume(UUID_1);
    Resume resumeUUID2 = new Resume(UUID_2);
    Resume resumeUUID3 = new Resume(UUID_3);
    Resume resumeUUID4 = new Resume(UUID_4);
    private int i;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resumeUUID1);
        storage.save(resumeUUID2);
        storage.save(resumeUUID3);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume resumeToUpdate = new Resume(UUID_1);

        storage.update(resumeToUpdate);
        assertSame(resumeToUpdate, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        Resume resumeToUpdate = new Resume();
        storage.update(resumeToUpdate);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] result = storage.getAll();
        assertEquals(resumeUUID1, result[0]);
        assertEquals(resumeUUID2, result[1]);
        assertEquals(resumeUUID3, result[2]);
    }

    @Test
    public void save() throws Exception {
        storage.save(resumeUUID4);
        assertEquals(4, storage.size());
        Resume[] result = storage.getAll();
        assertEquals(resumeUUID1, result[0]);
        assertEquals(resumeUUID2, result[1]);
        assertEquals(resumeUUID3, result[2]);
        assertEquals(resumeUUID4, result[3]);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(resumeUUID1);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        int i = 4;
        try {
            for (; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            fail("Overflow occurred ahead of time");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }

    @Test
    public void get() throws Exception {
        assertEquals(UUID_1, storage.get(UUID_1).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }
}