package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.logic.commands.exceptions.CommandException;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.activity.Activity;
import nasa.model.activity.UniqueActivityList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

public class ContinueCommandTest {

    private Model model = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());

    @Test
    public void execute_validCommandUnfilteredList_success() throws CommandException {
        Module module = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ACTIVITY, moduleCode);
        ContinueCommand continueCommand = new ContinueCommand(INDEX_FIRST_ACTIVITY, moduleCode);

        String expectedMessage = String.format(ContinueCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());
        doneCommand.execute(expectedModel);
        doneCommand.execute(model); // set the activity to done for it to be undone

        setActivityToDone(expectedModel, moduleCode, INDEX_FIRST_ACTIVITY);
        assertCommandSuccess(continueCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleNotFoundUnfilteredList_throwsCommandException() {
        Module module = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();

        // a module that does not exist
        ContinueCommand continueCommand = new ContinueCommand(INDEX_FIRST_ACTIVITY, new ModuleCode("MA1101R"));
        assertCommandFailure(continueCommand, model, ContinueCommand.MESSAGE_MODULE_NOT_FOUND);

        // an index that is wrong
        continueCommand = new ContinueCommand(Index.fromOneBased(10000), moduleCode);
        assertCommandFailure(continueCommand, model, ContinueCommand.MESSAGE_ACTIVITY_NOT_FOUND);

        // a module that is already undone
        continueCommand = new ContinueCommand(INDEX_FIRST_ACTIVITY, moduleCode);
        ModelManager expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());
        assertCommandFailure(continueCommand, expectedModel, ContinueCommand.MESSAGE_ACTIVITY_ALREADY_UNDONE);
    }

    private void setActivityUndone(ModelManager modelManager, ModuleCode moduleCode, Index index) {
        Module module = modelManager.getModule(moduleCode);
        UniqueActivityList activityList = module.getActivities();
        Activity activity = activityList.getActivityByIndex(index);
        activity.setUndone();
    }

    private void setActivityToDone(ModelManager modelManager, ModuleCode moduleCode, Index index) {
        Module module = modelManager.getModule(moduleCode);
        UniqueActivityList activityList = module.getActivities();
        Activity activity = activityList.getActivityByIndex(index);
        activity.setDone();
    }
}
