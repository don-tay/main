package nasa.storage;

import static nasa.testutil.TypicalModules.getTypicalNasaBook;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import nasa.commons.core.GuiSettings;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonNasaBookStorage nasaBookStorage = new JsonNasaBookStorage(getTempFilePath("ab"),
                getTempFilePath("cd"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(nasaBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void nasaBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonNasaBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonNasaBookStorageTest} class.
         */
        NasaBook original = getTypicalNasaBook();
        storageManager.saveNasaBook(original);
        ReadOnlyNasaBook retrieved = storageManager.readNasaBook().get();
        assertEquals(original, new NasaBook(retrieved));
    }

    @Test
    public void getNasaBookFilePath() {
        assertNotNull(storageManager.getNasaBookFilePath());
    }

}
