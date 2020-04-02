package nasa.model;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.LogsCenter;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Name;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.SortMethod;
import nasa.model.module.UniqueModuleList;

/**
 * Represents the in-memory model of the NASA data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final NasaBook nasaBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;
    private final HistoryManager<UniqueModuleList> historyManager;

    /**
     * Initializes a ModelManager with the given NasaBook and userPrefs.
     */
    public ModelManager(ReadOnlyNasaBook nasaBook, ReadOnlyHistory<UniqueModuleList> historyBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(nasaBook, userPrefs);

        logger.fine("Initializing with NASA: " + nasaBook + " and user prefs " + userPrefs);

        this.nasaBook = new NasaBook(nasaBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredModules = new FilteredList<>(this.nasaBook.getModuleList());
        this.historyManager = new HistoryManager(historyBook);
        initialisation();
    }

    public ModelManager() {
        this(new NasaBook(), new HistoryBook<UniqueModuleList>(), new UserPrefs());
    }

    public HistoryManager<UniqueModuleList> getHistoryManager() {
        return historyManager;
    }

    /**
     * Startup setup for Nasa book.
     */
    public void initialisation() {
        updateSchedule();
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    /**
     * Update the history manager list every time there is a change.
     */
    public void updateHistory() {
        final UniqueModuleList temp = new UniqueModuleList();
        temp.setModules(nasaBook.getDeepCopyList());
        historyManager.add(temp);
    }

    /**
     * Update the schedule for each activity.
     */
    public void updateSchedule() {
        nasaBook.scheduleAll();
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
        updateHistory();
    }

    @Override
    public void undoHistory() {
        if (historyManager.undo()) {
            nasaBook.setModuleList(historyManager.getItem());
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        }
    }

    @Override
    public void redoHistory() {
        if (historyManager.redo()) {
            nasaBook.setModuleList(historyManager.getItem());
            updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
        }
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getNasaBookFilePath() {
        return userPrefs.getNasaBookFilePath();
    }

    @Override
    public void setNasaBookFilePath(Path nasaBookFilePath) {
        requireNonNull(nasaBookFilePath);
        userPrefs.setNasaBookFilePath(nasaBookFilePath);
    }

    //=========== NasaBook ================================================================================

    @Override
    public void setNasaBook(ReadOnlyNasaBook nasaBook) {
        this.nasaBook.resetData(nasaBook);
    }

    @Override
    public ReadOnlyNasaBook getNasaBook() {
        return nasaBook;
    }

    @Override
    public ReadOnlyHistory getHistoryBook() {
        return historyManager.getHistoryBook();
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return nasaBook.hasModule(module);
    }

    @Override
    public boolean hasModule(ModuleCode module) {
        requireNonNull(module);
        return nasaBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        nasaBook.removeModule(target);
    }

    @Override
    public void deleteModule(ModuleCode target) {
        nasaBook.removeModule(target);
        updateHistory();
    }

    @Override
    public void removeModuleByIndex(Index index) {
        nasaBook.removeModuleByIndex(index);
        updateHistory();
    }

    @Override
    public void addModule(Module module) {
        nasaBook.addModule(module);
        updateHistory();
        //historyManager.add(new UniqueModuleList().setModules(nasaBook.getList()));
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModule(ModuleCode moduleCode, ModuleName moduleName) {
        nasaBook.addModule(moduleCode, moduleName);
        updateHistory();
        //historyManager.add(new UniqueModuleList().setModules(nasaBook.getList()));
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
        updateHistory();
    }

    @Override
    public void setModule(ModuleCode target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        nasaBook.setModule(target, editedModule);
        updateHistory();
    }

    @Override
    public void addActivity(Module target, Activity activity) {
        nasaBook.addActivity(target, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addActivity(ModuleCode target, Activity activity) {
        nasaBook.addActivity(target, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setActivityByIndex(Module module, Index index, Activity activity) {
        nasaBook.setActivityByIndex(module, index, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setActivityByIndex(ModuleCode module, Index index, Activity activity) {
        nasaBook.setActivityByIndex(module, index, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void editActivityByIndex(Module module, Index index, Object... args) {
        nasaBook.editActivityByIndex(module, index, args);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void editActivityByIndex(ModuleCode module, Index index, Object... args) {
        nasaBook.editActivityByIndex(module, index, args);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivity(Module target, Activity activity) {
        nasaBook.removeActivity(target, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivity(ModuleCode target, Activity activity) {
        nasaBook.removeActivity(target, activity);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivityByIndex(Module target, Index index) {
        nasaBook.removeActivityByIndex(target, index);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void removeActivityByIndex(ModuleCode target, Index index) {
        nasaBook.removeActivityByIndex(target, index);
        updateHistory();
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }
    /*
        @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        nasaBook.setPerson(target, editedPerson);
    }
     */

    @Override
    public boolean hasActivity(ModuleCode target, Activity activity) {
        requireAllNonNull(target, activity);
        return nasaBook.hasActivity(target, activity);
    }

    @Override
    public void setSchedule(ModuleCode module, Name activity, Index type) {
        requireAllNonNull(module, activity, type);
        nasaBook.setSchedule(module, activity, type);
        updateHistory();
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedNasaBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList(Index index) {
        Module module = filteredModules.get(index.getZeroBased());
        return module.getDeepCopyList();
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList(ModuleCode moduleCode) {
        updateFilteredModuleList(x -> x.getModuleCode().equals(moduleCode));
        Module module = filteredModules.get(0);
        return module.getDeepCopyList();
    }

    @Override
    public void updateFilteredActivityList(Index index, Predicate<Activity> predicate) {
        Module module = filteredModules.get(index.getZeroBased());
        module.updateFilteredActivityList(predicate);
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        for (Module module : filteredModules) {
            module.updateFilteredActivityList(predicate);
        }
    }

    @Override
    public void sortActivityList(SortMethod sortMethod) {
        requireNonNull(sortMethod);
        for (Module module : filteredModules) {
            module.sortActivityList(sortMethod);
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return nasaBook.equals(other.nasaBook)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules);
    }

    @Override
    public Module getModule(ModuleCode moduleCode) {
        UniqueModuleList uniqueModuleList = nasaBook.getUniqueModuleList();
        return uniqueModuleList.getModule(moduleCode);
    }
}
