package nasa.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.Name;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.SortMethod;
import nasa.model.module.UniqueModuleList;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} for modules that always evaluate to true */
    Predicate<Module> PREDICATE_SHOW_ALL_MODULES = unused -> true;

    /** {@code Predicate} for activities that always evaluate to true */
    Predicate<Activity> PREDICATE_SHOW_ALL_ACTIVITIES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     * @param userPrefs ReadOnlyUserPrefs
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     * @return ReadOnlyUserPrefs
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     * @return GuiSettings
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     * @param guiSettings GuiSettings
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     * @return Path
     */
    Path getNasaBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     * @param nasaBookFilePath Path
     */
    void setNasaBookFilePath(Path nasaBookFilePath);

    /**
     * Replaces address book data with the data in {@code nasaBook}.
     * @param nasaBook ReadOnlyNasaBook
     */
    void setNasaBook(ReadOnlyNasaBook nasaBook);

    /** Returns the NasaBook
     * @return ReadOnlyNasaBook
     */
    ReadOnlyNasaBook getNasaBook();

    /** Returns the HistoryBook
     * @return ReadOnlyHistory
     */
    ReadOnlyHistory getHistoryBook();

    Module getModule(ModuleCode moduleCode);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     * @param module Module
     * @return boolean
     */
    boolean hasModule(Module module);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     * @param moduleCode ModuleCode
     * @return boolean
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Deletes the given module.
     * The module must exist in the nasa book.
     * @param target Module
     */
    void deleteModule(Module target);

    /**
     * Deletes the given module.
     * The module must exist in the nasa book.
     * @param target ModuleCode
     */
    void deleteModule(ModuleCode target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the nasa book.
     * @param module Module
     */
    void addModule(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the nasa book.
     * @param moduleCode ModuleCode
     * @param moduleName ModuleName
     */
    void addModule(ModuleCode moduleCode, ModuleName moduleName);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the nasa book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     * @param target Module
     * @param editedModule Module
     */
    void setModule(Module target, Module editedModule);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the nasa book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     * @param target ModuleCode
     * @param editedModule Module
     */
    void setModule(ModuleCode target, Module editedModule);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist in the nasa book.
     * @param target Module
     * @param activity Activity
     */
    void addActivity(Module target, Activity activity);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist in the nasa book.
     * @param target ModuleCode
     * @param activity Activity
     */
    void addActivity(ModuleCode target, Activity activity);

    /**
     * Remove the given activity.
     * {@code activity} must not already exist in the nasa book.
     * @param target Module
     * @param activity Activity
     */
    void removeActivity(Module target, Activity activity);

    /**
     * Remove the given activity.
     * {@code activity} must not already exist in the nasa book.
     * @param target ModuleCode
     * @param activity Activity
     */
    void removeActivity(ModuleCode target, Activity activity);

    /**
     * Returns true if a module code {@code target} has {@code activity} exists in the nasa book.
     * @param target ModuleCode
     * @param activity Activity
     * @return boolean
     */
    boolean hasActivity(ModuleCode target, Activity activity);

    /**
     * Returns true if a module code {@code target} has activity {@code name} exists in the nasa book.
     * @param target ModuleCode
     * @param name Name
     * @return boolean
     */
    boolean hasActivity(ModuleCode target, Name name);

    /**
     * Replaces the given activity in {@code target} with {@code editedActivity}.
     * {@code target} must exist in the nasa book.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity
     * in the address book.
     */
    // void setActivity(Activity target, Activity editedActivity);

    /** Returns an unmodifiable view of the filtered activity list from the module at {@code index} of
     * the NasaBook's {@code UniqueModuleList}.
     * @param index Index
     * @return ObservableList
     */
    ObservableList<Activity> getFilteredActivityList(Index index);

    ObservableList<Activity> getFilteredActivityList(ModuleCode moduleCode);

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     * @param index Index
     * @param predicate Predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Index index, Predicate<Activity> predicate);

    void updateFilteredActivityList(Predicate<Activity> predicate);

    /** Returns an unmodifiable view of the filtered module list
     * @return ObservableList
     */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
     * @param predicate Predicate
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredModuleList(Predicate<Module> predicate);

    /**
     * Sorts filtered activity list of all modules by method specified by sortMethod
     * @param sortMethod The method of sorting.
     */
    void sortActivityList(SortMethod sortMethod);
    void setActivityByIndex(Module module, Index index, Activity activity);
    void setActivityByIndex(ModuleCode moduleCode, Index index, Activity activity);
    void editActivityByIndex(Module module, Index index, Object... args);
    void editActivityByIndex(ModuleCode moduleCode, Index index, Object... args);
    void removeModuleByIndex(Index index);
    void removeActivityByIndex(Module module, Index index);
    void removeActivityByIndex(ModuleCode moduleCode, Index index);
    HistoryManager<UniqueModuleList> getHistoryManager();
    void undoHistory();
    boolean redoHistory();
    boolean setSchedule(ModuleCode module, Name activity, Index type);
    String quote();
}
