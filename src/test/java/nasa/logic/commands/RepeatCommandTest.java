package nasa.logic.commands;

import static nasa.testutil.Assert.assertThrows;
import static nasa.testutil.TypicalModules.CS2103T;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nasa.commons.core.index.Index;

import nasa.model.Model;
import nasa.model.ModelManager;
import nasa.model.activity.Name;

class RepeatCommandTest {

    private Model model = new ModelManager();

    @Test
    public void constructor_nullModule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RepeatCommand(null, null, null));
    }

    @Test
    public void constructor_scheduleIndexOutOfBounds() {

        Name name = new Name("Homework");

        Index correctIndex = Index.fromZeroBased(1);
        Index wrongIndex = Index.fromZeroBased(4);

        model.addModule(CS2103T);

        CommandResult expectedResult = new CommandResult(String.format("%s for %s %s %s", RepeatCommand.MESSAGE_SUCCESS,
                CS2103T.getModuleCode(), name, correctIndex.getZeroBased()));

        RepeatCommand repeatCommand = new RepeatCommand(CS2103T.getModuleCode(), name, correctIndex);

        RepeatCommand incorrectRepeatCommand = new RepeatCommand(CS2103T.getModuleCode(), name, wrongIndex);

        try {
            assertEquals(expectedResult, repeatCommand.execute(model));
        } catch (Exception error) {
            System.out.println("Error in test case");
        }
        assertThrows(IllegalArgumentException.class, () -> incorrectRepeatCommand.execute(model));
    }
}
