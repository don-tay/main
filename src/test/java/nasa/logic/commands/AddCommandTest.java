package nasa.logic.commands;

import static java.util.Objects.requireNonNull;
import static nasa.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import nasa.commons.core.GuiSettings;
import nasa.commons.core.index.Index;
import nasa.model.HistoryManager;
import nasa.model.Model;
import nasa.model.NasaBook;
import nasa.model.ReadOnlyHistory;
import nasa.model.ReadOnlyNasaBook;
import nasa.model.ReadOnlyUserPrefs;
import nasa.model.activity.Activity;
import nasa.model.activity.Name;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;
import nasa.model.module.ModuleName;
import nasa.model.module.SortMethod;
import nasa.model.module.UniqueModuleList;
import nasa.testutil.ModuleBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddModuleCommand(null));
    }

    /*
    @Test
    public void execute_moduleAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingModuleAdded modelStub = new ModelStubAcceptingModuleAdded();
        Module validModule = new ModuleBuilder().build();

        CommandResult commandResult = new AddModuleCommand(validModule).execute(modelStub);

        assertEquals(String.format(AddModuleCommand.MESSAGE_SUCCESS, validModule), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validModule), modelStub.modulesAdded);
    }

     */

    /*
    @Test
    public void execute_duplicateModule_throwsCommandException() {
        Module validModule = new ModuleBuilder().build();
        AddModuleCommand addCommand = new AddModuleCommand(validModule);
        ModelStub modelStub = new ModelStubWithModule(validModule);

        assertThrows(CommandException.class, AddModuleCommand.MESSAGE_DUPLICATED_MODULE, () ->
                addCommand.execute(modelStub));
    }

     */

    @Test
    public void equals() {
        Module cs2103t = new ModuleBuilder().withCode("CS2103T").build();
        Module cs2101 = new ModuleBuilder().withCode("CS2101").build();
        AddModuleCommand addCS2103TCommand = new AddModuleCommand(cs2103t);
        AddModuleCommand addCS2101Command = new AddModuleCommand(cs2101);

        // same object -> returns true
        assertTrue(addCS2103TCommand.equals(addCS2103TCommand));

        // same values -> returns true
        AddModuleCommand addCS2103TCommandCopy = new AddModuleCommand(cs2103t);
        assertTrue(addCS2103TCommand.equals(addCS2103TCommandCopy));

        // different types -> returns false
        assertFalse(addCS2103TCommand.equals(1));

        // null -> returns false
        assertFalse(addCS2103TCommand.equals(null));

        // different module -> returns false
        assertFalse(addCS2103TCommand.equals(addCS2101Command));
    }


    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getNasaBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNasaBookFilePath(Path nasaBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setNasaBook(ReadOnlyNasaBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyNasaBook getNasaBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModule(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(Module target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteModule(ModuleCode target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public HistoryManager<UniqueModuleList> getHistoryManager() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList(ModuleCode moduleCode) {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ReadOnlyHistory getHistoryBook() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public void undoHistory() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean redoHistory() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public boolean setSchedule(ModuleCode module, Name activity, Index type) {
            throw new AssertionError("This method should not be called");
        }
    }

    /**
     * A Model stub that contains a single module.
     */
    private class ModelStubWithModule extends ModelStub {
        private final Module module;

        ModelStubWithModule(Module module) {
            requireNonNull(module);
            this.module = module;
        }

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
        }

        @Override
        public void addModule(ModuleCode moduleCode, ModuleName moduleName) {
            requireNonNull(moduleCode);
            requireNonNull(moduleName);
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(ModuleCode target, Module editedModule) {

        }

        @Override
        public void addActivity(Module target, Activity activity) {

        }

        @Override
        public void addActivity(ModuleCode target, Activity activity) {

        }

        @Override
        public void removeActivity(Module target, Activity activity) {

        }

        @Override
        public void removeActivity(ModuleCode target, Activity activity) {

        }

        @Override
        public boolean hasActivity(ModuleCode target, Activity activity) {
            return false;
        }

        @Override
        public boolean hasActivity(ModuleCode target, Name name) {
            return false;
        }

        @Override
        public void setActivityByIndex(Module module, Index index, Activity activity) {

        }

        @Override
        public void setActivityByIndex(ModuleCode moduleCode, Index index, Activity activity) {

        }

        @Override
        public void editActivityByIndex(Module module, Index index, Object... args) {

        }

        @Override
        public void editActivityByIndex(ModuleCode moduleCode, Index index, Object... args) {

        }

        @Override
        public ObservableList<Activity> getFilteredActivityList(Index index) {
            return null;
        }

        @Override
        public void updateFilteredActivityList(Index index, Predicate<Activity> predicate) {

        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {

        }

        @Override
        public void removeModuleByIndex(Index index) {

        }

        @Override
        public void removeActivityByIndex(Module module, Index index) {

        }

        @Override
        public void removeActivityByIndex(ModuleCode moduleCode, Index index) {

        }

        @Override
        public boolean setSchedule(ModuleCode module, Name activity, Index type) {
            return false;
        }

        @Override
        public String quote() {
            return null;
        }

        @Override
        public void sortActivityList(SortMethod sortMethod) {

        }

        @Override
        public Module getModule(ModuleCode moduleCode) {
            return new Module(new ModuleCode("CS1231"), new ModuleName("Test"));
        }

        /*
        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return this.module.isSameModule(module);
        }

         */
    }

    /**
     * A Model stub that always accept the module being added.
     */
    private class ModelStubAcceptingModuleAdded extends ModelStub {
        final ArrayList<Module> modulesAdded = new ArrayList<>();

        /*
        @Override
        public boolean hasModule(Module module) {
            requireNonNull(module);
            return modulesAdded.stream().anyMatch(module::isSameModule);
        }

         */

        @Override
        public void addModule(Module module) {
            requireNonNull(module);
            modulesAdded.add(module);
        }

        @Override
        public void addModule(ModuleCode moduleCode, ModuleName moduleName) {
            requireNonNull(moduleCode);
            requireNonNull(moduleName);
            modulesAdded.add(new Module(moduleCode, moduleName));
        }

        @Override
        public void setModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setModule(ModuleCode target, Module editedModule) {

        }

        @Override
        public Module getModule(ModuleCode target) {
            return null;
        }

        @Override
        public void addActivity(Module target, Activity activity) {

        }

        @Override
        public void addActivity(ModuleCode target, Activity activity) {

        }

        @Override
        public void removeActivity(Module target, Activity activity) {

        }

        @Override
        public void removeActivity(ModuleCode target, Activity activity) {

        }

        @Override
        public boolean hasActivity(ModuleCode target, Activity activity) {
            return false;
        }

        @Override
        public boolean hasActivity(ModuleCode target, Name name) {
            return false;
        }

        @Override
        public void setActivityByIndex(Module module, Index index, Activity activity) {

        }

        @Override
        public void setActivityByIndex(ModuleCode moduleCode, Index index, Activity activity) {

        }

        @Override
        public void editActivityByIndex(Module module, Index index, Object... args) {

        }

        @Override
        public void editActivityByIndex(ModuleCode moduleCode, Index index, Object... args) {

        }

        @Override
        public ObservableList<Activity> getFilteredActivityList(Index index) {
            return null;
        }

        @Override
        public ObservableList<Activity> getFilteredActivityList(ModuleCode moduleCode) {
            return null;
        }

        @Override
        public void updateFilteredActivityList(Index index, Predicate<Activity> predicate) {

        }

        @Override
        public void updateFilteredActivityList(Predicate<Activity> predicate) {

        }

        @Override
        public void removeModuleByIndex(Index index) {

        }

        @Override
        public void removeActivityByIndex(Module module, Index index) {

        }

        @Override
        public void removeActivityByIndex(ModuleCode moduleCode, Index index) {

        }

        @Override
        public HistoryManager<UniqueModuleList> getHistoryManager() {
            return null;
        }

        @Override
        public void undoHistory() {

        }

        @Override
        public boolean redoHistory() {
            return true;
        }

        @Override
        public ReadOnlyNasaBook getNasaBook() {
            return new NasaBook();
        }

        @Override
        public ReadOnlyHistory getHistoryBook() {
            return null;
        }

        @Override
        public boolean setSchedule(ModuleCode module, Name activity, Index type) {
            return false;
        }

        @Override
        public String quote() {
            return null;
        }

        @Override
        public void sortActivityList(SortMethod sortMethod) {

        }
    }
}
