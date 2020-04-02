package nasa.model.module;

import static java.util.Objects.requireNonNull;
import static nasa.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import nasa.commons.core.index.Index;
import nasa.model.activity.Activity;
import nasa.model.activity.UniqueActivityList;
import nasa.model.module.exceptions.DuplicateModuleException;
import nasa.model.module.exceptions.ModuleNotFoundException;

/**
 * A list of modules that enforces uniqueness between its elements and does not allow nulls.
 * A Module is considered unique by comparing using {@code Module#equals(Module)}.
 * As such, adding and updating of Module uses Module#equals(Module)
 * for equality so as to ensure that the Module being added or updated is
 * unique in terms of identity in the UniqueModuleList. However, the removal of a Module uses
 * Module#equals(Object) so as to ensure that the Module with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueModuleList implements Iterable<Module> {

    private final ObservableList<Module> internalList = FXCollections.observableArrayList();
    private final ObservableList<Module> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Module as the given argument.
     * @param toCheck Module
     * @return boolean
     */
    public boolean contains(Module toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent ModuleCode as the given argument.
     * @param toCheck ModuleCode
     * @return boolean
     */
    public boolean contains(ModuleCode toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(x -> x.getModuleCode().equals(toCheck));
    }

    /**
     * Adds a Module to the list.
     * The Module must not already exist in the list.
     * @param toAdd Module
     */
    public void add(Module toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateModuleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Adds a ModuleCode to the list.
     * The ModuleCode must not already exist in the list.
     * @param toAddCode ModuleCode
     * @param toAddName ModuleName
     */
    public void add(ModuleCode toAddCode, ModuleName toAddName) {
        requireAllNonNull(toAddCode, toAddName);
        if (contains(toAddCode)) {
            throw new DuplicateModuleException();
        }
        internalList.add(new Module(toAddCode, toAddName));
    }

    /**
     * Replaces the Module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The Module identity of {@code editedModule} must not be the same as another existing Module in the list.
     * @param target Module
     * @param editedModule Module
     */
    public void setModule(Module target, Module editedModule) {
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.equals(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Replaces the ModuleCode {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the list.
     * The ModuleCode identity of {@code editedModule} must not be the same as another existing Module in the list.
     * @param target ModuleCode
     * @param editedModule Module
     */
    public void setModule(ModuleCode target, Module editedModule) {
        int index = internalList.indexOf(getModule(target));
        if (index == -1) {
            throw new ModuleNotFoundException();
        }

        if (!target.equals(editedModule) && contains(editedModule)) {
            throw new DuplicateModuleException();
        }

        internalList.set(index, editedModule);
    }

    /**
     * Removes the equivalent Module from the list.
     * The Module must exist in the list.
     * @param toRemove Module
     */
    public void remove(Module toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes the equivalent ModuleCode from the list.
     * The ModuleCode must exist in the list.
     * @param toRemove ModuleCode
     */
    public void remove(ModuleCode toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(getModule(toRemove))) {
            throw new ModuleNotFoundException();
        }
    }

    /**
     * Removes the Module based on index.
     * @param index Index
     */
    public void removeByIndex(Index index) {
        internalList.remove(index.getZeroBased());
    }

    public UniqueModuleList setModules(UniqueModuleList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        return this;
    }

    /**
     * Replaces the contents of this list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     * @param modules List
     */
    public void setModules(List<Module> modules) {
        requireAllNonNull(modules);
        if (!modulesAreUnique(modules)) {
            throw new DuplicateModuleException();
        }

        internalList.setAll(modules);
    }

    public void setActivityByIndex(Module module, Index index, Activity activity) {
        requireNonNull(activity);

        Module moduleSelected = getModule(module);
        moduleSelected.setActivityByIndex(index, activity);
    }

    public void setActivityByIndex(ModuleCode moduleCode, Index index, Activity activity) {
        requireNonNull(activity);

        Module moduleSelected = getModule(moduleCode);
        moduleSelected.setActivityByIndex(index, activity);
    }

    /**
     * Edits activity based on index and module.
     * @param module Module of the activity
     * @param index index of the activity in list
     * @param args parameters to be edited
     */
    public void editActivityByIndex(Module module, Index index, Object... args) {
        requireNonNull(args);

        Module moduleSelected = getModule(module);
        moduleSelected.editActivityByIndex(index, args);
    }

    /**
     * Edits activity based on index and moduleCode.
     * @param moduleCode ModuleCode of activity
     * @param index index of the activity in list
     * @param args parameters to be edited
     */
    public void editActivityByIndex(ModuleCode moduleCode, Index index, Object... args) {
        requireNonNull(args);

        Module moduleSelected = getModule(moduleCode);
        moduleSelected.editActivityByIndex(index, args);
    }

    public UniqueActivityList getActivities(Module module) {
        requireAllNonNull(module);
        return getModule(module).getActivities();
    }

    public UniqueActivityList getActivities(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);
        return getModule(moduleCode).getActivities();
    }

    /**
     * get a particular module from the list
     * @param module Module
     * @return Module
     */
    public Module getModule(Module module) {
        requireAllNonNull(module);
        return internalList.parallelStream()
                .filter(x -> x.equals(module))
                .findFirst()
                .get();
    }

    /**
     * get a particular module from the list
     * @param moduleCode ModuleCode
     * @return Module
     */
    public Module getModule(ModuleCode moduleCode) {
        requireAllNonNull(moduleCode);
        return internalList.parallelStream()
                .filter(x -> x.getModuleCode().equals(moduleCode))
                .findFirst()
                .orElse(null);
    }

    public ObservableList<Module> getDeepCopyList() {
        ObservableList<Module> deepCopyList = FXCollections.observableArrayList();
        for (Module mods : internalUnmodifiableList) {
            Module moduleTemp = new Module(mods.getModuleCode(), mods.getModuleName());
            moduleTemp.setActivities(mods.getActivities().getDeepCopyList());
            deepCopyList.add(moduleTemp);
        }
        return deepCopyList;
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     * @return ObservableList
     */
    public ObservableList<Module> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public ObservableList<Module> asModifiableObservableList() {
        return internalList;
    }

    @Override
    public Iterator<Module> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueModuleList // instanceof handles nulls
                && internalList.equals(((UniqueModuleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code modules} contains only unique modules.
     * @param modules List
     * @return boolean
     */
    private boolean modulesAreUnique(List<Module> modules) {
        for (int i = 0; i < modules.size() - 1; i++) {
            for (int j = i + 1; j < modules.size(); j++) {
                if (modules.get(i).equals(modules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
