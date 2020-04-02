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
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getNasaBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setNasaBookFilePath(Path nasaBookFilePath);

    /**
     * Replaces address book data with the data in {@code nasaBook}.
     */
    void setNasaBook(ReadOnlyNasaBook nasaBook);

    /** Returns the NasaBook */
    ReadOnlyNasaBook getNasaBook();

    /** Returns the HistoryBook */
    ReadOnlyHistory getHistoryBook();

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(Module module);

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    boolean hasModule(ModuleCode moduleCode);

    /**
     * Deletes the given module.
     * The module must exist in the nasa book.
     */
    void deleteModule(Module target);

    /**
     * Deletes the given module.
     * The module must exist in the nasa book.
     */
    void deleteModule(ModuleCode target);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the nasa book.
     */
    void addModule(Module module);

    /**
     * Adds the given module.
     * {@code module} must not already exist in the nasa book.
     */
    void addModule(ModuleCode moduleCode, ModuleName moduleName);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the nasa book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    void setModule(Module target, Module editedModule);

    /**
     * Replaces the given module {@code target} with {@code editedModule}.
     * {@code target} must exist in the nasa book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    void setModule(ModuleCode target, Module editedModule);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist in the nasa book.
     */
    void addActivity(Module target, Activity activity);

    /**
     * Adds the given activity.
     * {@code activity} must not already exist in the nasa book.
     */
    void addActivity(ModuleCode target, Activity activity);

    /**
     * Remove the given activity.
     * {@code activity} must not already exist in the nasa book.
     */
    void removeActivity(Module target, Activity activity);

    /**
     * Remove the given activity.
     * {@code activity} must not already exist in the nasa book.
     */
    void removeActivity(ModuleCode target, Activity activity);

    /**
     * Returns true if a module code {@code target} has {@code activity} exists in the nasa book.
     */
    boolean hasActivity(ModuleCode target, Activity activity);

    /**
     * Replaces the given activity in {@code target} with {@code editedActivity}.
     * {@code target} must exist in the nasa book.
     * The activity identity of {@code editedActivity} must not be the same as another existing activity
     * in the address book.
     */
    // void setActivity(Activity target, Activity editedActivity);

    /** Returns an unmodifiable view of the filtered activity list from the module at {@code index} of
     * the NasaBook's {@code UniqueModuleList}.
     */
    ObservableList<Activity> getFilteredActivityList(Index index);

    ObservableList<Activity> getFilteredActivityList(ModuleCode moduleCode);

    /**
     * Updates the filter of the filtered activity list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredActivityList(Index index, Predicate<Activity> predicate);

    void updateFilteredActivityList(Predicate<Activity> predicate);

    /** Returns an unmodifiable view of the filtered module list */
    ObservableList<Module> getFilteredModuleList();

    /**
     * Updates the filter of the filtered module list to filter by the given {@code predicate}.
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
    void redoHistory();
    void setSchedule(ModuleCode module, Name activity, Index type);
    Module getModule(ModuleCode moduleCode);
}
