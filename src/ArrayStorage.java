import java.util.Arrays;
import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int numberOfResumes = 0;

    void clear() {
        Arrays.fill(storage, null);
        numberOfResumes = 0;
    }

    void save(Resume r) {
        storage[numberOfResumes++] = r;
    }

    Resume get(String uuid) {
        for (int i = 0; i < numberOfResumes; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < numberOfResumes; i++) {
            if (Objects.equals(storage[i].uuid, uuid)) {
                numberOfResumes -= 1;
                System.arraycopy(storage, i + 1, storage, i, numberOfResumes - i);
                storage[numberOfResumes] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, numberOfResumes);
    }

    int size() {
        return numberOfResumes;
    }
}