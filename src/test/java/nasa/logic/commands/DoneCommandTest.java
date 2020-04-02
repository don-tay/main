package nasa.logic.commands;

import static nasa.logic.commands.CommandTestUtil.assertCommandFailure;
import static nasa.logic.commands.CommandTestUtil.assertCommandSuccess;
import static nasa.testutil.TypicalIndexes.INDEX_FIRST_ACTIVITY;
import static nasa.testutil.TypicalModules.getTypicalNasaBook;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;
import nasa.model.HistoryBook;
import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.UserPrefs;
import nasa.model.activity.Activity;
import nasa.model.activity.UniqueActivityList;
import nasa.model.module.Module;
import nasa.model.module.ModuleCode;

public class DoneCommandTest {

    private Model model = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());

    @Test
    public void execute_validCommandUnfilteredList_success() {
        Module module = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ACTIVITY, moduleCode);

        String expectedMessage = String.format(DoneCommand.MESSAGE_SUCCESS);

        ModelManager expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());
        setActivityToDone(expectedModel, moduleCode, INDEX_FIRST_ACTIVITY);

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_moduleNotFoundUnfilteredList_throwsCommandException() {
        Module module = model.getFilteredModuleList().get(INDEX_FIRST_ACTIVITY.getZeroBased());
        ModuleCode moduleCode = module.getModuleCode();

        // a module that does not exist
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_ACTIVITY, new ModuleCode("MA1101R"));
        assertCommandFailure(doneCommand, model, doneCommand.MESSAGE_MODULE_NOT_FOUND);

        // an index that is wrong
        doneCommand = new DoneCommand(Index.fromOneBased(10000), moduleCode);
        assertCommandFailure(doneCommand, model, doneCommand.MESSAGE_ACTIVITY_NOT_FOUND);

        // a module that is already done
        doneCommand = new DoneCommand(INDEX_FIRST_ACTIVITY, moduleCode);
        ModelManager expectedModel = new ModelManager(getTypicalNasaBook(), new HistoryBook<>(), new UserPrefs());
        setActivityToDone(expectedModel, moduleCode, INDEX_FIRST_ACTIVITY);
        assertCommandFailure(doneCommand, expectedModel, doneCommand.MESSAGE_ACTIVITY_ALREADY_DONE);
    }

    private void setActivityToDone(ModelManager modelManager, ModuleCode moduleCode, Index index) {
        Module module = modelManager.getModule(moduleCode);
        UniqueActivityList activityList = module.getActivities();
        Activity activity = activityList.getActivityByIndex(index);
        activity.setDone();
    }
}
